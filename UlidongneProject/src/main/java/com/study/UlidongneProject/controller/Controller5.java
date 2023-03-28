package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.MemberRepository;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Controller
public class Controller5 {

    private final Service3 service3;
    private final MemberRepository memberRepository;
    @GetMapping("/testheejin")
    public String testheejin(Model model) {

        return "/testheejin";
    }

    @ResponseBody
    @GetMapping("/boards")
    public MemberEntity findAll(){
        MemberEntity memberEntity = service3.findById("01012345678");
        return memberEntity;
    }
}
