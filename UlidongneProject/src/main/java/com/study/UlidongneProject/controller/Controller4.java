package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.entity.MemberEntity;
//import com.study.UlidongneProject.entity.MemberRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


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
        // 성공하면 memberEntity, 실패하면 null로 보내기
        List<MemberEntity>  memberList = memberRepository.findAll();
        System.out.println("findAll 수행");
        return memberList;
    }

    @ResponseBody
    @GetMapping("/member/idx/{memberIdx}")
    public MemberEntity findById(@PathVariable("memberIdx") Long memberIdx) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findById(memberIdx).orElse(null);
        return memberEntity;
    }

    @ResponseBody
    @GetMapping("/member/phone/{memberPhone}")
    public MemberEntity findByPhone(@PathVariable("memberPhone") String memberPhone) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findByPhone(memberPhone);
        return memberEntity;
    }

    @ResponseBody
    @PostMapping("/member")
    public Boolean save(@RequestBody HashMap<String, String> map) {

        if(true) { // 등록 성공하면
            return true;
        }else { // 등록 실패하면
            return false;
        }
    }

    @ResponseBody
    @PutMapping("/member")
    public Boolean update(@RequestBody HashMap<String, String> map) {

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
