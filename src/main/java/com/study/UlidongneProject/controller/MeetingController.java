package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.MeetingSaveRequestDto;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.MeetingServiceImpl;
import com.study.UlidongneProject.service.MemberServiceImpl;
import com.study.UlidongneProject.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class MeetingController {
    private final MemberServiceImpl memberService;
    private final MeetingServiceImpl meetingService;

    @GetMapping("/club/{param}/meeting")
    public String meetingForm(@AuthenticationPrincipal User user, @PathVariable("param") Long clubIdx, Model model) {
        String userPhone = user.getUsername();
        MemberEntity memberEntity = memberService.findByUserPhone(userPhone);
        model.addAttribute("clubIdx", clubIdx);
        model.addAttribute("memberDto", memberEntity);
        return "clubContent/makeMeeting";
    }

    @ResponseBody
    @PostMapping("/meeting")
    public Boolean meeting(MeetingSaveRequestDto dto) {
        return meetingService.create(dto);
    }

    @PatchMapping("/meeting")
    @ResponseBody
    public int MeetingJoinMember(@RequestBody HashMap<String, String> data){
        return meetingService.join(data);
    }

    @PatchMapping("/meeting/nonappearance")
    @ResponseBody
    public boolean MeetingQuitMember(@RequestBody HashMap<String, String> data){
        return meetingService.quit(data);
    }
}
