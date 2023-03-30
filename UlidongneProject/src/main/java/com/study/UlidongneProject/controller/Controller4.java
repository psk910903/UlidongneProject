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

    // findAll
    @ResponseBody
    @GetMapping("/member")
    public List<MemberEntity> findAll() {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        List<MemberEntity>  memberList = memberRepository.findAll();
        return memberList;
    }

    // 찾기
    @ResponseBody
    @GetMapping("/member/location/{keyword}")
    public List<MemberEntity> search(@PathVariable("keyword") String keyword) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        List<MemberEntity>  memberList = memberRepository.findAll();
        return memberList;
    }

    // findById
    @ResponseBody
    @GetMapping("/member/idx/{memberIdx}")
    public MemberEntity findById(@PathVariable("memberIdx") Long memberIdx) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findById(memberIdx).orElse(null);
        return memberEntity;
    }

    // findByPhone
    @ResponseBody
    @GetMapping("/member/phone/{memberPhone}")
    public MemberEntity findByPhone(@PathVariable("memberPhone") String memberPhone) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findByPhone(memberPhone);
        return memberEntity;
    }

    // save
    @ResponseBody
    @PostMapping("/member")
    public Boolean save(@RequestBody MemberEntity memberEntity) {

        if(true) { // 등록 성공하면
            return true;
        }else { // 등록 실패하면
            return false;
        }
    }

    // update
    @ResponseBody
    @PutMapping("/member")
    public Boolean update(@RequestBody MemberEntity memberEntity) {

        if(true) { // 수정 성공하면
            return true;
        }else { // 수정 실패하면
            return false;
        }
    }

    // delete
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
