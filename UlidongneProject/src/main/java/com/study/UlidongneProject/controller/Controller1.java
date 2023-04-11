package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Interface.ChatService;
import com.study.UlidongneProject.service.Service1;
import com.study.UlidongneProject.service.Service2;
import com.study.UlidongneProject.service.Service3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controller1 {
    private final Service1 service1;
    private final Service3 service3;
    private final Service2 service2;
    private final ChatService chatService;
    private final MemberRepository memberRepository;

    @GetMapping("/config")
    public String configPage(){
        return "/seeMore/setting/config";
    }

    @GetMapping("/notice/list")
    public String noticeList(Model model){
        model.addAttribute("notice", service1.findNoticeList());
        return "/seeMore/setting/notice";
    }

    @GetMapping("/notice/{noticeIdx}")
    public String notice(@PathVariable("noticeIdx") Long noticeIdx, Model model){
        model.addAttribute("notice", service1.findNoticeByIdx(noticeIdx));
        return "/seeMore/setting/noticeContent";
    }

    @GetMapping("/club/{param}") // 클럽 정보 조회
    public String clubDetailPage(@PathVariable("param") Long clubIdx, HttpSession session, Model model){

        MemberResponseDto memberDto = service2.findMemberByIdxWOClubRepo((Long) session.getAttribute("memberIdx"));
        ClubResponseDto clubResponseDto = service1.findClubByIdx(clubIdx);
        List<MemberResponseDto> memList = service1.findClubMemberList(clubIdx);
        List<MeetingResponseDto> meetingList = service1.findMeetingByClubIdx(clubIdx);
        List<MemberResponseDto> clubWaitGuest = service1.findClubWaitMember(clubIdx);
        List<ChattingResponseDto> chattingList = chatService.findByClubIdx(clubIdx);
        List<String> memberPictureList = chatService.findMemberPictureByClubIdx(clubIdx);
        List<String> memberNameList = chatService.findMemberNameByClubIdx(clubIdx);
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

    @GetMapping("/member/{param}") // 회원 정보 조회
    public String memberDetailPage(@PathVariable("param") Long memberIdx, Model model){
        MemberResponseDto memberResponseDto = service1.findMemberByIdx(memberIdx);
        model.addAttribute("member", memberResponseDto);
        model.addAttribute("clubList", memberResponseDto.getClubList()); // member만 보냈더니 th:inline으로 못받음. 이유 모름
        return "clubContent/memberInfo";
    }

    @GetMapping("/member/{memberIdx}/location")
    public String searchPage(){
        return "clubList/searchLocation";
    }

    @GetMapping("/club/location")
    public String searchClubPage( Model model){

        return "clubList/searchClubLocation";
    }

    @GetMapping("/join/location")
    public String searchJoinPage(){
        return "clubList/searchJoinLocation";
    }

    @GetMapping("/member/{memberIdx}/information")
    public String changeMemberLocation(@RequestParam(required = false) String address,
                                       @PathVariable("memberIdx") Long memberIdx,
                                       Model model){
        MemberResponseDto memberResponseDto = service1.findMemberByIdx(memberIdx);
        if(address != null) {
            List<String> addressList = Arrays.stream(address.split(" ")).toList();
            model.addAttribute("address", addressList);
            model.addAttribute("member", memberResponseDto);
        }else {
            List<String> addressList = Arrays.stream( memberResponseDto.getMemberLocation().split(" ") ).toList();
            model.addAttribute("address", addressList);
            model.addAttribute("member", memberResponseDto);
        }
        return "seeMore/myProfile";
    }

    @GetMapping("/member/{memberIdx}/more")
    public String seeMorePage(@PathVariable("memberIdx") Long memberIdx, Model model){
        MemberResponseDto memberDto = service1.findMemberByIdx(memberIdx);
        model.addAttribute("member",memberDto);
        return "seeMore/seeMore";
    }

    @GetMapping("/member/{memberIdx}/club")
    public String memberJoinedClub(@PathVariable("memberIdx") Long memberIdx, Model model){
        List<ClubResponseDto> clubList = service1.findMemberJoinedClub(memberIdx);
        model.addAttribute("club",clubList);
        return "/seeMore/myJoinedClub";
    }

    @GetMapping("/member/{memberIdx}/category")
    public String memberCategory(@PathVariable("memberIdx") Long memberIdx, Model model){
        List<CategoryResponseDto> categoryDto = service1.findCategory();
        MemberResponseDto memberDto = service1.findMemberByIdx(memberIdx);
        model.addAttribute("category", categoryDto);
        model.addAttribute("member", memberDto);
        return "/seeMore/editMyCategory";
    }

    @GetMapping("/member/activity/{memberIdx}")
    public String myActivity(@PathVariable("memberIdx") Long memberIdx, Model model){
        MemberResponseDto dto =  service1.findMemberByIdx(memberIdx);
        model.addAttribute("member", dto);
        model.addAttribute("club", service1.findMemberJoinedClub(memberIdx));
        model.addAttribute("category", service1.findMyInterestCategory(memberIdx));
        model.addAttribute("recoClub", service1.findMyRecommendClub(memberIdx));
        model.addAttribute("location", dto.getMemberLocation().split(" ")[0]);
        return "/myActivity/myActivity";
    }


}
