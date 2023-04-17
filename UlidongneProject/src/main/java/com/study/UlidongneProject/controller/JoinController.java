package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.CategoryResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.dto.ZipcodeDto;
import com.study.UlidongneProject.service.CategoryService;
import com.study.UlidongneProject.service.MemberServiceImpl;
import com.study.UlidongneProject.service.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final SearchServiceImpl searchService;
    private final MemberServiceImpl memberService;
    private final CategoryService categoryService;

    @GetMapping("/join/location")
    public String searchJoinPage(){
        return "clubList/searchJoinLocation";
    }

    @GetMapping("/join/location/keyword/{keyword}/{what}")
    @ResponseBody
    public List<ZipcodeDto> joiLocationSearch(@PathVariable("keyword") String keyword){ // 가입 위치 검색
        return searchService.findLocation(keyword);
    }

    @GetMapping("/join")
    public String joinForm(@RequestParam(required = false) String address, Model model) {
        if(address != null) {
            List<String> addressList = Arrays.stream(address.split(" ")).toList();
            model.addAttribute("address", addressList);
        }
        return "joinForm";
    }

    @ResponseBody
    @PostMapping("/join/action")
    public String joinAction(MemberSaveRequestDto dto, HttpSession session){
        try {
            String result = memberService.join(dto);
            if(!result.equals( "-1") && !result.equals( "0")) {
                session.setAttribute("memberPhone", dto.getMemberPhone());
                session.setAttribute("memberName", dto.getMemberName());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @GetMapping("/join/{param}/category")
    public String joinCategory(@PathVariable("param") Long memberIdx, Model model) {
        List<CategoryResponseDto> categoryDto = categoryService.findCategory();
        MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
        model.addAttribute("category", categoryDto);
        model.addAttribute("member", memberDto);
        return "seeMore/editMyCategory";
    }

    @GetMapping("/join/terms")
    public String joinTerms() {
        return "terms";
    }
}
