package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Interface.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final SecurityService securityService;
    private final MemberRepository memberRepository;
    private final MemberServiceImpl memberService;
    private final MeetingRepository meetingRepository;

    @Override
    public boolean create(ClubSaveRequestDto dto, User user) {

        dto.setClubCreateDate(LocalDate.now());
        dto.setClubLocation(PublicMethod.location(dto.getClubLocation()));
        ClubEntity entity = dto.toSaveEntity();
        try {
            //클럽생성
            clubRepository.save(entity);
            //저장한 클럽 idx 가져와서 회원이 가입한 클럽에 추가
            Long clubIdx = clubRepository.clubOrderByDateLimit1().getClubIdx();
            MemberEntity memberEntity = memberService.findByUserPhone(user.getUsername());
            MemberResponseDto memberDto = memberService.findMemberByIdx(memberEntity.getMemberIdx());

            String joinedClub = memberDto.getJoinedClub();
            if (joinedClub.equals("{}")) {
                memberDto.setJoinedClub("{" + clubIdx + "}");
            } else {
                // "}" 삭제 후 가입한 인덱스 추가
                String substring = joinedClub.substring(0, joinedClub.length() - 1);
                memberDto.setJoinedClub(substring + "," + clubIdx + "}");
            }
            MemberEntity entity1 = memberDto.toUpdateEntity();
            System.out.println("entity1 = " + entity1);
            memberRepository.save(entity1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean requestToJoin(Long clubIdx, Long memberIdx){ // 가입 요청 보내기
        ClubResponseDto clubDto = findClubByIdx(clubIdx);
        MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
        String clubWait = clubDto.getClubWaitGuest();
        String memberWait = memberDto.getWaitClub();
        if(PublicMethod.stringToLongList(clubDto.getClubGuest()).contains(memberIdx)){ // 이미 가입된 경우 무조건 거부
            return false;
        }
        if(!PublicMethod.stringToLongList(clubWait).contains(memberIdx)) { // 유저가 해당 클럽에 가입 신청을 하지 않았을때
            if (clubWait.length() > 2) { // club 대기열에 넣기
                clubWait = clubWait.substring(0, clubWait.length() - 1) + "," + memberIdx + "}";
            } else if (clubWait.length() == 2) {
                clubWait = clubWait.substring(0, clubWait.length() - 1) + memberIdx + "}";
            }
            clubDto.setClubWaitGuest(clubWait);
            clubRepository.save(clubDto.toUpdateEntity());
            if (memberWait.length() > 2) { // member 대기열에 넣기
                memberWait = memberWait.substring(0, memberWait.length() - 1) + "," + clubIdx + "}";
            } else if (memberWait.length() == 2) {
                memberWait = memberWait.substring(0, memberWait.length() - 1) + clubIdx + "}";
            }
            memberDto.setWaitClub(memberWait);
            memberRepository.save(memberDto.toUpdateEntity());
            return true;
        }else { // 이미 가입 신청을 한 경우
            return false;
        }
    }

    @Transactional
    public boolean join(Long clubIdx, Long memberIdx){ // 클럽 가입 수락
        try{
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
            List<Long> clubMember = PublicMethod.stringToLongList(clubDto.getClubGuest());
            List<Long> membersClub = PublicMethod.stringToLongList(memberDto.getJoinedClub());
            clubMember.add(memberIdx);
            membersClub.add(clubIdx);
            memberDto.setJoinedClub(PublicMethod.longListToString(membersClub));
            clubDto.setClubGuest(PublicMethod.longListToString(clubMember));
            List<Long> memberWait = PublicMethod.stringToLongList(memberDto.getWaitClub());
            List<Long> clubWait = PublicMethod.stringToLongList(clubDto.getClubWaitGuest());
            memberWait.remove(clubIdx);
            clubWait.remove(memberIdx);
            clubDto.setClubWaitGuest(PublicMethod.longListToString(clubWait));
            memberDto.setWaitClub(PublicMethod.longListToString(memberWait));
            clubRepository.save(clubDto.toUpdateEntity());
            memberRepository.save(memberDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


    @Transactional
    @Override
    public int quit(Long clubIdx, Long memberIdx) { // 클럽 탈퇴 로직. 예외처리 애매.
        try {
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
            List<Long> clubMember = PublicMethod.stringToLongList(clubDto.getClubGuest());
            List<Long> membersClub = PublicMethod.stringToLongList(memberDto.getJoinedClub());
            clubMember.remove(memberIdx);
            membersClub.remove(clubIdx);
            if(clubMember.size()==0){  // 클럽 인원이 없으면 클럽 삭제
                clubRepository.deleteById(clubIdx);
                List<MeetingEntity> clubMeetingList = meetingRepository.findByMeetingClub(clubIdx); //클럽 삭제될 때 일정 삭제
                for (MeetingEntity entity : clubMeetingList) {
                    meetingRepository.delete(entity);
                }
                memberDto.setJoinedClub(PublicMethod.longListToString(membersClub));
                memberRepository.save(memberDto.toUpdateEntity());
                List<Long> clubWaitMember = PublicMethod.stringToLongList(clubDto.getClubWaitGuest());
                for (Long waitMember : clubWaitMember) {
                    MemberResponseDto memberResponseDto = memberService.findMemberByIdx(waitMember);
                    List<Long> memberWaitClub = PublicMethod.stringToLongList(memberResponseDto.getWaitClub());
                    memberWaitClub.remove(clubIdx);
                    memberResponseDto.setWaitClub(PublicMethod.longListToString(memberWaitClub));
                    memberRepository.save(memberDto.toUpdateEntity());
                }
                return 0;
            } else {
                if (clubDto.getClubHost() == memberIdx) { // 클럽장이 나갔을시, 다음 회원에게 자동 인계
                    clubDto.setClubHost(clubMember.get(0));
                }
                clubDto.setClubGuest(PublicMethod.longListToString(clubMember));
                memberDto.setJoinedClub(PublicMethod.longListToString(membersClub));
                memberRepository.save(memberDto.toUpdateEntity());
                List<MeetingEntity> meetingList = meetingRepository.findByMeetingClub(clubIdx);
                for (MeetingEntity entity : meetingList) { // 참여하기로 한 미팅에서 삭제
                    Long meetingIdx = entity.getMeetingIdx();
                    MeetingPatchDto meetingDto = new MeetingPatchDto(meetingRepository.findByMeetingIdx(Long.toString(meetingIdx)));
                    List<Long> meetingJoinMemberList = PublicMethod.stringToLongList(meetingDto.getMeetingAttend());
                    meetingJoinMemberList.remove(memberIdx);
                    meetingDto.setMeetingAttend(PublicMethod.longListToString(meetingJoinMemberList));
                    meetingRepository.save(meetingDto.toUpdateEntity());
                }
                clubRepository.save(clubDto.toUpdateEntity());
            }
            return 1;
        }catch (Exception e){
            System.out.println(e);
            System.out.println("클럽 탈퇴 로직 오류");
            return 2;
        }
    }

    @Transactional
    public boolean rejectToJoin(Long clubIdx, Long memberIdx){ // 클럽 가입 거절
        try{
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
            List<Long> clubWait = PublicMethod.stringToLongList(clubDto.getClubWaitGuest());
            List<Long> memWait = PublicMethod.stringToLongList(memberDto.getWaitClub());
            clubWait.remove(memberIdx);
            memWait.remove(clubIdx);
            clubDto.setClubWaitGuest(PublicMethod.longListToString(clubWait));
            memberDto.setWaitClub(PublicMethod.longListToString(memWait));
            memberRepository.save(memberDto.toUpdateEntity());
            clubRepository.save(clubDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            System.out.println("클럽 가입 거절 오류");
            return false;
        }
    }

    @Override
    public void modify() {

    }

    @Override
    public void chatting() {

    }

    @Override
    public void kick() {

    }

    @Transactional(readOnly = true)
    public ClubResponseDto findClubByIdx(Long clubIdx){ // pk값으로 클럽 찾기
        ClubEntity entity = new ClubEntity();
        ClubResponseDto dto = null;
        try{
            entity = clubRepository.findById(clubIdx).get();
            dto = new ClubResponseDto(entity);
            dto.setClubGuestLong(PublicMethod.stringToLongList(dto.getClubGuest()));
        }catch (Exception e){
            System.out.println(e);
        }
        return dto;
    }

    public List<ClubResponseDto> orderBy(String orderBy) {
        List<ClubEntity> clubListEntity;
        if (orderBy.equals("people")) { // 회원많은순
            clubListEntity = clubRepository.clubOrderByPeople();
        }else{ // date 최신순
            clubListEntity = clubRepository.clubOrderByDate();
        }
        return settingClubLocation(clubListEntity);
    }

    public List<ClubResponseDto> settingClubLocation(List<ClubEntity> clubListEntity) {

        List<ClubResponseDto> clubList = clubListEntity.stream().map(ClubResponseDto::new).collect(Collectors.toList());
        for (ClubResponseDto clubDto : clubList) {
            clubDto.setMembers(clubDto.getClubGuest().split(",").length);
            clubDto.setClubLocation(PublicMethod.locationLastArray(clubDto.getClubLocation()));
        }
        return clubList;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findClubWaitMember(Long clubIdx){ // 클럽 대기 맴버 찾기
        List<MemberResponseDto> list = new ArrayList<>();
        try {
            ClubEntity entity = clubRepository.findById(clubIdx).get();
            List<Long> waitMember = PublicMethod.stringToLongList( entity.getClubWaitGuest());
            if(waitMember.size()>0){
                for(Long memIdx : waitMember){
                    MemberEntity memberEntity =memberRepository.findById(memIdx).get();
                    list.add(new MemberResponseDto(memberEntity));
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<ClubResponseDto> findMemberJoinedClub(Long memberIdx){ // 맴버가 가입한 클럽 찾기
        List<ClubResponseDto> clubList = new ArrayList<>();
        try {
            MemberResponseDto memberDto = new MemberResponseDto(memberRepository.findById(memberIdx).get());
            List<Long> memberJoinedClubIdxList = PublicMethod.stringToLongList(memberDto.getJoinedClub());
            for(Long clubIdx : memberJoinedClubIdxList){
                ClubResponseDto club = new ClubResponseDto(clubRepository.findById(clubIdx).get());
                club.setMembers(PublicMethod.stringToLongList(club.getClubGuest()).size());
                club.setClubLocation(PublicMethod.locationLastArray(club.getClubLocation()));
                clubList.add( club );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return clubList;
    }

    @Transactional(readOnly = true)
    public List<List<ClubResponseDto>> findMyRecommendClub(Long memberIdx){ // 내 카테고리 클럽 찾기
        List<List<ClubResponseDto>> clubsList = new ArrayList<>();
        try{
            List<CategoryResponseDto> memberCategory = memberService.findMyInterestCategory(memberIdx);
            for(CategoryResponseDto cateOne : memberCategory){
                List<ClubResponseDto> dtoList = new ArrayList<>();
                List<ClubEntity> clubEntityList = clubRepository.findTop5ByClubCategory(cateOne.getCategoryMain());
                if(clubEntityList.size()>0) {
                    for (ClubEntity entity : clubEntityList) {
                        ClubResponseDto dto2 = new ClubResponseDto(entity);
                        dto2.setMembers(PublicMethod.stringToLongList(dto2.getClubGuest()).size());
                        dto2.setClubLocation(PublicMethod.locationLastArray(dto2.getClubLocation()));
                        dtoList.add(dto2);
                    }
                    clubsList.add(dtoList);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return clubsList;
    }

    public boolean memberQuit(Long idx) {
        try {
            MemberEntity entity = memberRepository.findById(idx).get();
            for (Long clubIdx : PublicMethod.stringToLongList(entity.getWaitClub())) {
                rejectToJoin(clubIdx, entity.getMemberIdx());
            }
            for (Long clubIdx : PublicMethod.stringToLongList(entity.getJoinedClub())) {
                quit(clubIdx, entity.getMemberIdx());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List<ClubResponseDto> findTop10ByClubCategoryLocation(String category, String location) {
        List<ClubEntity> entityList = clubRepository.findByClubCategoryLocation(category, location);

        List<ClubResponseDto> dtoList = settingClubLocation(entityList);

        List<ClubResponseDto> top10List = new ArrayList<>();
        for (int i=0; i < dtoList.size() && i < 10; i++){
            top10List.add(dtoList.get(i));
        }

        return top10List;
    }
}
