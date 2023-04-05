package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.*;
import com.study.UlidongneProject.entity.repository.*;
import com.study.UlidongneProject.other.PublicMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;

@Service
@RequiredArgsConstructor
public class Service1 {
    private final ClubRepository clubRepository;
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;
    private final ZipcodeRepository zipcodeRepository;
    private final AwsS3Service awsS3Service;
    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public ClubResponseDto findClubByIdx(Long idx){ // pk값으로 클럽 찾기
        ClubEntity entity = new ClubEntity();
        try{
            entity = clubRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ClubResponseDto(entity) ;
    }

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findMeetingByClubIdx(Long idx){ // 클럽 pk값으로 미팅 찾기
        List<MeetingResponseDto> dtoList = new ArrayList<>();
        try{
            System.out.println("aaaaaaaaaaaaa");
            List<MeetingEntity> entityList = meetingRepository.findByMeetingClub(idx);
            if(entityList.size()>0) {
                for (MeetingEntity entity : entityList) {
                    if(entity.getMeetingDate().isAfter(LocalDate.now())) {
                        dtoList.add(new MeetingResponseDto(entity, this));
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return dtoList ;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findClubMemberList(Long idx){ // 클럽 pk값으로 가입 회원 리스트 찾기
        List<MemberResponseDto> dtoList = new ArrayList<>();
        ClubEntity clubEntity = clubRepository.findById(idx).get();
        List<Long> clubMember = PublicMethod.stringToLongList(clubEntity.getClubGuest());
        for(Long memIdx : clubMember){
             dtoList.add( new MemberResponseDto( memberRepository.findById(memIdx).get()));
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMeetingMemberList(Long meetingIdx){ // 미팅 번호로 참여 회원 리스트 찾기
        List<MemberResponseDto> dtoList = new ArrayList<>();
        MeetingEntity meetingEntity = meetingRepository.findById(meetingIdx).get();
        List<Long> meetingMember = PublicMethod.stringToLongList( meetingEntity.getMeetingAttend());
        for(Long memberIdx : meetingMember){
            MemberEntity entity = memberRepository.findById(memberIdx).get();
            MemberResponseDto dto = new MemberResponseDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByIdx(Long idx){ // pk값으로 맴버 찾기
        MemberEntity entity = null;
        try {
            entity = memberRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        MemberResponseDto dto = new MemberResponseDto(entity);
        dto.setClubRepository(clubRepository);
        dto.arrToClubDto(entity);
        return dto;
    }

    @Transactional
    public boolean makeClubJoinAsk(Long clubIdx, Long memberIdx){ // 가입 요청 보내기
        ClubResponseDto clubDto = findClubByIdx(clubIdx);
        MemberResponseDto memberDto = findMemberByIdx(memberIdx);
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
    public boolean outClub( Long clubIdx, Long memberIdx) { // 클럽 탈퇴 로직. 예외처리 애매.
        try {
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = findMemberByIdx(memberIdx);
            List<Long> clubMember = PublicMethod.stringToLongList(clubDto.getClubGuest());
            List<Long> membersClub = PublicMethod.stringToLongList(memberDto.getJoinedClub());
            clubMember.remove(memberIdx);
            membersClub.remove(clubIdx);
            clubDto.setClubGuest(PublicMethod.LongListToString(clubMember));
            memberDto.setJoinedClub(PublicMethod.LongListToString(membersClub));
            clubRepository.save(clubDto.toUpdateEntity());
            memberRepository.save(memberDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Transactional
    public boolean joinClub(Long clubIdx, Long memberIdx){ // 클럽 가입 수락
        try{
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = findMemberByIdx(memberIdx);
            List<Long> clubMember = PublicMethod.stringToLongList(clubDto.getClubGuest());
            List<Long> membersClub = PublicMethod.stringToLongList(memberDto.getJoinedClub());
            clubMember.add(memberIdx);
            membersClub.add(clubIdx);
            memberDto.setJoinedClub(PublicMethod.LongListToString(membersClub));
            clubDto.setClubGuest(PublicMethod.LongListToString(clubMember));
            List<Long> memberWait = PublicMethod.stringToLongList(memberDto.getWaitClub());
            List<Long> clubWait = PublicMethod.stringToLongList(clubDto.getClubWaitGuest());
            memberWait.remove(clubIdx);
            clubWait.remove(memberIdx);
            clubDto.setClubWaitGuest(PublicMethod.LongListToString(clubWait));
            memberDto.setWaitClub(PublicMethod.LongListToString(memberWait));
            clubRepository.save(clubDto.toUpdateEntity());
            memberRepository.save(memberDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Transactional
    public boolean rejectClub(Long clubIdx, Long memberIdx){ // 클럽 가입 거절
        try{
            ClubResponseDto clubDto = findClubByIdx(clubIdx);
            MemberResponseDto memberDto = findMemberByIdx(memberIdx);
            List<Long> clubWait = PublicMethod.stringToLongList(clubDto.getClubWaitGuest());
            List<Long> memWait = PublicMethod.stringToLongList(memberDto.getWaitClub());
            clubWait.remove(memberIdx);
            memWait.remove(clubIdx);
            clubDto.setClubWaitGuest(PublicMethod.LongListToString(clubWait));
            memberDto.setWaitClub(PublicMethod.LongListToString(memWait));
            memberRepository.save(memberDto.toUpdateEntity());
            clubRepository.save(clubDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
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

    @Transactional
    public List<ZipcodeDto> findLocation(String keyword){ // 위치 찾기
        List<ZipcodeDto> zipcodes = new ArrayList<>();
        try{
           for(Zipcode zipcode : zipcodeRepository.findByKeyword(keyword)){
               zipcodes.add(new ZipcodeDto(zipcode));
           }
        }catch (Exception e){
            System.out.println(e);
        }
        return zipcodes;
    }

    @Transactional
    public MemberResponseDto updateMemberInfo(Long idx , HttpServletRequest request, MultipartFile memberPicture){ // 유저 정보 수정
        MemberResponseDto dto = findMemberByIdx(idx);
        dto.setMemberGender(request.getParameter("memberName"));
        dto.setMemberGender(request.getParameter("memberGender"));
        dto.setMemberLocation(request.getParameter("memberLocation"));
        dto.setMemberIntroduce(request.getParameter("memberIntroduce"));
        String url = awsS3Service.upload(memberPicture);
        new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(url).
                build(), HttpStatus.OK);
        dto.setMemberPicture(url);
        memberRepository.save(dto.toUpdateEntity());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ClubResponseDto> findMemberJoinedClub(Long memberIdx){ // 맴버가 가입한 클럽 찾기
        List<ClubResponseDto> clubList = new ArrayList<>();
        try {
            MemberResponseDto dto = new MemberResponseDto(memberRepository.findById(memberIdx).get());
            List<Long> memberJoinedClub = PublicMethod.stringToLongList(dto.getJoinedClub());
            for(Long clubIdx : memberJoinedClub){
                ClubResponseDto club = new ClubResponseDto(clubRepository.findById(clubIdx).get());
                club.setMembers(PublicMethod.stringToLongList(club.getClubGuest()).size());
                clubList.add( club );
            }
        }catch (Exception e){
            System.out.println(e);
        }
       return clubList;
    }

    @Transactional
    public List<CategoryResponseDto> findCategory() {
        List<CategoryResponseDto> dtoList = new ArrayList<>();
        try {
            List<CategoryEntity> entityList = categoryRepository.findAll();
            for (CategoryEntity entity : entityList) {
                dtoList.add(new CategoryResponseDto(entity));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return dtoList;
    }
}
