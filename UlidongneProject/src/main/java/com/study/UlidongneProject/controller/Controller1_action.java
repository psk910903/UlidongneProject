package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.dto.ZipcodeDto;
import com.study.UlidongneProject.entity.Zipcode;
import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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
    @GetMapping("/location/member/keyword/{keyword}/{what}")
    @ResponseBody
    public List<ZipcodeDto> locationSearch(@PathVariable("keyword") String keyword){ // 위치 검색
        return service1.findLocation(keyword);
    }

    @PutMapping( value = "/member/{memberIdx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public boolean updateMemberInfo(@PathVariable("memberIdx") Long idx, // 맴버 정보 수정
                                    @RequestParam(value = "memberPicture", required = false) MultipartFile picture,
                                    HttpServletRequest request,
                                    Model model){
        MemberResponseDto dto= service1.updateMemberInfo(idx, request, picture);
        model.addAttribute("member", dto);
        return true;
    }

    @PatchMapping("/member/category/{memberIdx}")
    @ResponseBody
    public boolean updateMemberInterests(@PathVariable("memberIdx") Long idx,
                                         @RequestBody HashMap<String, String> data){
        return service1.changeMemberCategory(idx, data);
    }

}