package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.CategoryResponseDto;
import com.study.UlidongneProject.dto.FileResponse;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Interface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final MeetingRepository meetingRepository;
    private final CategoryService categoryService;

    @Override
    public String join(MemberSaveRequestDto dto) {

        Optional<MemberEntity> optional = memberRepository.optionalFindByPhone(dto.getMemberPhone());
        if (optional.isPresent()) {
            return "-1";
        } else {
            String url;
            if (dto.getFile() != null) {
                url = awsS3Service.upload(dto.getFile());

                new ResponseEntity<>(FileResponse.builder().
                        uploaded(true).
                        url(url).
                        build(), HttpStatus.OK);

                dto.setMemberPicture(url);
            }

            LocalDate date = PublicMethod.convertStringToLocalDate(dto.getMemberBirthdayStr());
            dto.setMemberBirthday(date);
            dto.setMemberJoinDate(LocalDate.now());

            String memberInterestCase4 = dto.getMemberInterestCase4();
            String memberInterestCase5 = dto.getMemberInterestCase5();
            if (memberInterestCase4.equals("")) {
                dto.setMemberInterestCase4(null);
            }
            if (memberInterestCase5.equals("")) {
                dto.setMemberInterestCase5(null);
            }
            dto.setMemberLocation(PublicMethod.location(dto.getMemberLocation()));
            MemberEntity entity = dto.toSaveEntity();
            System.out.println("entity = " + entity);

            memberRepository.save(entity);
            MemberEntity member = memberRepository.findByPhone(dto.getMemberPhone());
            return String.valueOf(member.getMemberIdx());
        }
    }

    @Override
    @Transactional
    public MemberResponseDto modify(Long idx , HttpServletRequest request, @RequestParam(required = false) MultipartFile memberPicture){ // 유저 정보 수정
        MemberResponseDto dto = findMemberByIdx(idx);
        dto.setMemberName(request.getParameter("memberName"));
        dto.setMemberGender(request.getParameter("memberGender"));
        dto.setMemberLocation(PublicMethod.location(request.getParameter("memberLocation")));
        dto.setMemberIntroduce(request.getParameter("memberIntroduce"));
        if(memberPicture != null){
            String url = awsS3Service.upload(memberPicture);
            new ResponseEntity<>(FileResponse.builder().
                    uploaded(true).
                    url(url).
                    build(), HttpStatus.OK);
            dto.setMemberPicture(url);
        }
        memberRepository.save(dto.toUpdateEntity());
        return dto;
    }

    @Transactional
    public MemberResponseDto modify(Long idx , HttpServletRequest request){ // 유저 정보 수정
        MemberResponseDto dto = findMemberByIdx(idx);
        dto.setMemberLocation(PublicMethod.location(request.getParameter("memberLocation")));
        memberRepository.save(dto.toUpdateEntity());
        return dto;
    }

    @Override
    @Transactional
    public boolean modifyCategory(Long memberIdx, HashMap<String, String> data){ // 관심 카테고리 수정
        MemberResponseDto memberResponseDto = findMemberByIdx(memberIdx);
        try {
            memberResponseDto.setMemberInterestCase1(data.get("memberInterestCase1"));
            memberResponseDto.setMemberInterestCase2(data.get("memberInterestCase2"));
            memberResponseDto.setMemberInterestCase3(data.get("memberInterestCase3"));
            memberResponseDto.setMemberInterestCase4(data.get("memberInterestCase4"));
            memberResponseDto.setMemberInterestCase5(data.get("memberInterestCase5"));
            memberRepository.save(memberResponseDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


    @Override
    @Transactional
    public boolean quit(Long idx, HttpSession session){ // 회원 탈퇴 하기
        try{
            MemberEntity entity = memberRepository.findById(idx).get();
            memberRepository.delete(entity);
            session.invalidate();
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        MemberEntity user = findByUserPhone(userPhone);
        if (user == null) {
            throw new UsernameNotFoundException(userPhone);
        }
        return new org.springframework.security.core.userdetails.User(user.getMemberPhone(),
                user.getMemberName(),
                Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByIdx(Long memberIdx){ // pk값으로 맴버 찾기
        MemberEntity memberEntity = null;
        try {
            memberEntity = memberRepository.findById(memberIdx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        MemberResponseDto memberDto = new MemberResponseDto(memberEntity);
        memberDto.setLocationLast(PublicMethod.locationLastArray(memberDto.getMemberLocation()));
        memberDto.setClubRepository(clubRepository);
        memberDto.arrToClubDto(memberEntity);
        return memberDto;
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
    public List<CategoryResponseDto> findMyInterestCategory(Long memberIdx){ // 내 관심 카테고리 찾기
        List<CategoryResponseDto> myInterest = new ArrayList<>();
        try{
            MemberResponseDto member = findMemberByIdx(memberIdx);
            List<CategoryResponseDto> allCate = categoryService.findCategory();
            for(CategoryResponseDto cate : allCate){
                if(cate.getCategoryImage().equals(member.getMemberInterestCase1()) ||
                        cate.getCategoryImage().equals(member.getMemberInterestCase2()) ||
                        cate.getCategoryImage().equals(member.getMemberInterestCase3()) ||
                        cate.getCategoryImage().equals(member.getMemberInterestCase4()) ||
                        cate.getCategoryImage().equals(member.getMemberInterestCase5())){
                    myInterest.add(cate);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return myInterest;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByIdxWOClubRepo(Long idx){ // pk값으로 맴버 찾기
        MemberEntity entity = null;
        try {
            entity = memberRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        MemberResponseDto dto = new MemberResponseDto(entity);
        return dto;
    }

    @Transactional(readOnly = true)
    public MemberEntity findByUserPhone(String phone) {
        return this.memberRepository.findByPhone(phone);
    }
}
