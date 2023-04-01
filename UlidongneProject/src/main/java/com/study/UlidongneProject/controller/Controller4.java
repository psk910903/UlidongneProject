package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.entity.MemberEntity;
//import com.study.UlidongneProject.entity.MemberRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Interface.SearchService;
import com.study.UlidongneProject.service.SearchServiceImpl;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/member/location/{keyword}/{page}")
    public List<MemberEntity> search(@PathVariable("keyword") String keyword, @PathVariable("page") int page) {
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

    // 확작성을 위해 반환형을 int 형식으로 바꿈
    // return: 0일 경우: ajax 통신 중 오류 발생(api.js에서 return 0)
    // return: 1일 경우: 일반적인 통신 성공
    // return: 2일 경우: 서버로 통신은 성공했지만 내부적인 오류가 생겼을 경우(일반적인 경우)
    // 그외 케이스를 나눌 경우 다른 숫자로 함수를 만들면 됨

    // save
    @ResponseBody
    @PostMapping("/member")
    public int save(@RequestBody MemberEntity memberEntity) {
        
        if(true) { // 등록 성공하면
            return 1;
        }else { // 등록 실패하면
            return 2;
        }
    }

    // update
    @ResponseBody
    @PutMapping("/member")
    public int update(@RequestBody MemberEntity memberEntity) {

        if(true) { // 수정 성공하면
            return 1;
        }else { // 수정 실패하면
            return 2;
        }
    }

    // delete
    @ResponseBody
    @DeleteMapping("/member/{memberIdx}")
    public int delete(@PathVariable("memberIdx") Long memberIdx) {

        if(true) { // 삭제 성공하면
            return 1;
        }else { // 삭제 실패하면
            return 2;
        }
    }




    //////////////////////////////////////// 희진 개인 작업

    private final SearchService searchService;

    @GetMapping("/search/keyword/{keyword}")
    public String searchKeyword(@PathVariable("keyword") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "/clubList/searchKeyword";
    }

    @ResponseBody
    @GetMapping("/club/keyword/{keyword}/{page}")
    public Page<ClubResponseDto> clubKeyword(@PathVariable("keyword") String keyword, @PathVariable("page") int page)  {

        Page<ClubResponseDto> clubList = searchService.findByKeyword(keyword, page);

        return clubList;
    }

}
