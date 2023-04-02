package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Service1;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controller1 {
    private final Service1 service1;
    private final MemberRepository memberRepository;

    @GetMapping("/club/{param}") // 클럽 정보 조회
    public String clubDetailPage(@PathVariable("param") Long clubIdx, Model model){
        ClubResponseDto clubResponseDto = service1.findClubByIdx(clubIdx);
        List<MemberResponseDto> memList = service1.findClubMemberList(clubIdx);
        List<MeetingResponseDto> meetingList = service1.findMeetingByClubIdx(clubIdx);
        List<MemberResponseDto> clubWaitGuest = service1.findClubWaitMember(clubIdx);
        model.addAttribute("club", clubResponseDto);
        model.addAttribute("member", memList);
        model.addAttribute("meeting", meetingList);
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
    public String searchPage(Model model){
        return "clubList/searchLocation";
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
            return "seeMore/myProfile";
        }else {
            List<String> addressList = Arrays.stream(memberResponseDto.getMemberLocation().split(" ")).toList();
            model.addAttribute("address", addressList);
            model.addAttribute("member", memberResponseDto);
            return "seeMore/myProfile";
        }
    }
//
//    @GetMapping("/member/{memberIdx}/inforamtino")
//    public String changeMemberInfo(@PathVariable("memberIdx") Long memberIdx, Model model){
//        MemberResponseDto memberResponseDto = service1.findMemberByIdx(memberIdx);
//        model.addAttribute("member", memberResponseDto);
//        return "seeMore/";
//    }
}
