package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.*;
import com.study.UlidongneProject.service.Interface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController {
    private final MemberServiceImpl memberService;
    private final ClubServiceImpl clubService;
    private final MeetingServiceImpl meetingService;
    private final ChatServiceImpl chatService;
    private final CategoryService categoryService;
    private final AwsS3Service awsS3Service;


    @GetMapping("/club/{param}") // 클럽 정보 조회
    public String clubDetailPage(@PathVariable("param") Long clubIdx, HttpSession session, Model model){
        MemberResponseDto memberDto = memberService.findMemberByIdxWOClubRepo((Long) session.getAttribute("memberIdx"));
        List<MemberResponseDto> memList = memberService.findClubMemberList(clubIdx);
        List<MeetingResponseDto> meetingList = meetingService.setImminentDay(meetingService.findMeetingByClubIdx(clubIdx));
        ClubResponseDto clubResponseDto = clubService.findClubByIdx(clubIdx);
        List<MemberResponseDto> clubWaitGuest = clubService.findClubWaitMember(clubIdx);
        List<ChattingResponseDto> chattingList = chatService.findByClubIdx(clubIdx);
        List<String> memberPictureList = chatService.findMemberPictureByClubIdx(clubIdx);
        List<String> memberNameList = chatService.findMemberNameByClubIdx(clubIdx);

        model.addAttribute("memberCount", memList.size());
        model.addAttribute("club", clubResponseDto);
        model.addAttribute("member", memList);
        model.addAttribute("meeting", meetingList);
        model.addAttribute("chattingList", chattingList);
        model.addAttribute("chattingMemberPictureList", memberPictureList);
        model.addAttribute("chattingMemberNameList", memberNameList);
        model.addAttribute("user", memberDto);
        if(clubWaitGuest.size()>0){
            model.addAttribute("waitingMember", clubWaitGuest);
        }

        return "clubContent/clubContent";
    }

    @GetMapping("/member/{memberIdx}/location")
    public String searchPage(){
        return "clubList/searchLocation";
    }

    @GetMapping("/club/location")
    public String searchClubPage( Model model){
        return "clubList/searchClubLocation";
    }

    @PatchMapping("/club/waiting/{clubIdx}")
    @ResponseBody
    public boolean clubJoinRequest(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){ // 클럽 대기열 추가
        Long memberIdx = Long.valueOf( data.get("memberIdx") );
        return clubService.requestToJoin(clubIdx, memberIdx);
    }


    @DeleteMapping("/club/{clubIdx}")
    @ResponseBody
    public int outClub( @PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){ // 클럽 탈퇴
        Long memberIdx = Long.valueOf(data.get("memberIdx"));

        return clubService.quit(clubIdx, memberIdx);
    }


    @PatchMapping("/club/{clubIdx}")
    @ResponseBody
    public boolean acceptMember(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data) { // 클럽 가입 승인
        Long memberIdx = Long.valueOf(data.get("memberIdx"));
        String accept = data.get("accept");
        if (accept.equals("Yes")) {
            return clubService.join(clubIdx, memberIdx);
        }else {
            return clubService.rejectToJoin(clubIdx,memberIdx);
        }
    }

    @GetMapping("/club")
    public String club(@RequestParam(required = false) String address , @AuthenticationPrincipal User user, Model model) { // 클럽 만들기 페이지

        List<CategoryEntity> category = categoryService.categoryFindAll();
        MemberEntity memberEntity = memberService.findByUserPhone(user.getUsername());
        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", category);

        // 작성자 서호준
        if(address != null) {
            List<String> addressList = Arrays.stream(address.split(" ")).toList();
            model.addAttribute("address", addressList);
        }
        return "clubList/makeClub";
    }

    @ResponseBody
    @PostMapping("/club")
    public Boolean save(ClubSaveRequestDto dto, @AuthenticationPrincipal User user) { // 클럽 만들기 동작

        String url = awsS3Service.upload(dto.getFile());

        new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(url).
                build(), HttpStatus.OK);

        dto.setClubProfileImage(url);
        return clubService.create(dto, user);
    }

}
