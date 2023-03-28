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
        return memberList;
    }

    @ResponseBody
    @GetMapping("/member/{memberIdx}")
    public MemberEntity findByPhone(@PathVariable("memberIdx") Long memberIdx) {
        MemberEntity memberEntity = memberRepository.findById(memberIdx).get();
        return memberEntity;
    }

    @ResponseBody
    @GetMapping("/member/phone/{memberPhone}")
    public MemberEntity findByPhone(@PathVariable("memberPhone") String memberPhone) {
        MemberEntity memberEntity = memberRepository.findByPhone(memberPhone);
        return memberEntity;
    }

    /*****************************/
    @ResponseBody
    @PostMapping("/member")
    public Boolean save() {
        // (@RequestParam String memberName, @RequestParam String memberPhone
        System.out.println("wwww");
        // memberRepository.save(entity);
        return true;
    }
//
//    @ResponseBody
//    @GetMapping("/boards")
//    public MemberEntity update() {
//        MemberEntity memberEntity = service3.findById("01012345678");
//        return memberEntity;
//    }
//
//    @DeleteMapping("/boards/{board_idx}")
//    public List<BoardResponseDto> delete(@PathVariable("board_idx") Long board_idx){
//        boardService.delete(board_idx);
//        return boardService.findAll();
//    }
}
