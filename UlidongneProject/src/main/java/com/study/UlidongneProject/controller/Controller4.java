package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.entity.MemberEntity;
//import com.study.UlidongneProject.entity.MemberRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class Controller4 {

    private final MemberRepository memberRepository;

    @GetMapping("/testheejin")
    public String testheejin(Model model) {

        return "/testheejin";
    }

    @ResponseBody
    @GetMapping("/member")
    public List<MemberEntity> findAll() {
        List<MemberEntity>  memberList = memberRepository.findAll();
        System.out.println("findAll 수행");
        return memberList;
    }

    @ResponseBody
    @GetMapping("/member/{memberIdx}")
    public MemberEntity findById(@PathVariable("memberIdx") Long memberIdx) {
        MemberEntity memberEntity = memberRepository.findById(memberIdx).get();
        System.out.println("findById 수행");
        return memberEntity;
    }

    @ResponseBody
    @GetMapping("/member/phone/{memberPhone}")
    public MemberEntity findByPhone(@PathVariable("memberPhone") String memberPhone) {
        MemberEntity memberEntity = memberRepository.findByPhone(memberPhone);
        System.out.println("findByPhone 수행");
        return memberEntity;
    }

    @ResponseBody
    @PostMapping("/member")
    public List<MemberEntity> save() {
        List<MemberEntity>  memberList = memberRepository.findAll();
        System.out.println("save 수행");
        return memberList;
    }

    @ResponseBody
    @PutMapping("/member")
    public Boolean update(MemberEntity memberEntity) {

        System.out.println("update 수행");

        if(true) { // 수정 성공하면
            return true;
        }else { // 수정 실패하면
            return false;
        }
    }

    @ResponseBody
    @DeleteMapping("/member/{memberIdx}")
    public Boolean delete(@PathVariable("memberIdx") Long memberIdx) {

        if(true) { // 삭제 성공하면
            return true;
        }else { // 삭제 실패하면
            return false;
        }
    }
}
