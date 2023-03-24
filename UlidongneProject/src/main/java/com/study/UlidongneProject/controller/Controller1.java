package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.entity.Member;
import com.study.UlidongneProject.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class Controller1 {

    private final MemberRepository memberRepository;
    @RequestMapping("/test")
    @ResponseBody
    private String test(){
        Member member = memberRepository.findById(1L).get();
        return member.getJoinedClub();
    }
}
