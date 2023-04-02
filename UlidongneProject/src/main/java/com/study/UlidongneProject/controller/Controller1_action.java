package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.dto.ZipcodeDto;
import com.study.UlidongneProject.entity.Zipcode;
import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class Controller1_action {
    private final Service1 service1;

    @PatchMapping("/club/waiting/{clubIdx}")
    @ResponseBody
    public boolean clubJoinRequest(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){ // 클럽 대기열 추가
        Long memberIdx = Long.valueOf( data.get("memberIdx") );
        return service1.makeClubJoinAsk(clubIdx, memberIdx);
    }

    @DeleteMapping("/club/{clubIdx}")
    @ResponseBody
    public boolean outClub( @PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){ // 클럽 탈퇴
        Long memberIdx = Long.valueOf(data.get("memberIdx"));
        return  service1.outClub(clubIdx, memberIdx);
    }

    @PatchMapping("/club/{clubIdx}")
    @ResponseBody
    public boolean acceptMember(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data) { // 클럽 가입 승인
        Long memberIdx = Long.valueOf(data.get("memberIdx"));
        String accept = data.get("accept");
        if (accept.equals("Yes")) {
            return service1.joinClub(clubIdx, memberIdx);
        }else {
            return service1.rejectClub(clubIdx,memberIdx);
        }
    }
    @GetMapping("/location/member/{keyword}/{what}")
    @ResponseBody
    public List<ZipcodeDto> locationSearch(@PathVariable("keyword") String keyword, Model model){ // 위치 검색
        return service1.findLocation(keyword);
    }

    @PutMapping("member/{memberIdx}")
    public boolean updateMemberInfo(@PathVariable("memberIdx") Long idx,
                                              MemberResponseDto memberResponseDto,
                                              Model model){
        MemberResponseDto dto = service1.updateMemberInfo(memberResponseDto);
        model.addAttribute("member", dto);
        return true;
    }
}