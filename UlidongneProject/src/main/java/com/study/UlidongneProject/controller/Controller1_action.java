package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class Controller1_action {
    private final Service1 service1;

    @PatchMapping("/club/waiting/{clubIdx}")
    @ResponseBody
    public boolean clubJoinRequest(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){
        Long memberIdx = Long.valueOf( data.get("memberIdx") );
        return service1.makeClubJoinAsk(clubIdx, memberIdx);
    }

    @DeleteMapping("/club/{clubIdx}")
    @ResponseBody
    public boolean outClub( @PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){
        Long memberIdx = Long.valueOf(data.get("memberIdx"));
        return  service1.outClub(clubIdx, memberIdx);
    }

    @PatchMapping("/club/{clubIdx}")
    @ResponseBody
    public boolean acceptMember(@PathVariable("clubIdx") Long clubIdx, @RequestBody HashMap<String, String> data){
        Long memberIdx = Long.valueOf(data.get("memberIdx"));
        return service1.joinClub(clubIdx, memberIdx);
    }
}