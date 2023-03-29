package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controller1 {
    private final Service1 service1;
    private final MemberRepository memberRepository;

    @GetMapping("/club/{param}")
    public String clubDetailPage(@PathVariable("param") Long clubIdx, Model model){
        ClubResponseDto clubResponseDto = service1.findClubByIdx(clubIdx);
        List<MemberResponseDto> memList = service1.findClubMemberList(clubIdx);
        List<MeetingResponseDto> meetingList = service1.findMeetingByClubIdx(clubIdx);
        model.addAttribute("club", clubResponseDto);
        model.addAttribute("member", memList);
        model.addAttribute("meeting", meetingList);
        return "clubContent/clubContent";
    }
}
