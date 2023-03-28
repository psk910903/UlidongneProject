package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.entity.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controller1 {
    private final Service1 service1;
    private final MemberRepository memberRepository;

    @RequestMapping("/test")
    public String test (){
        ClubResponseDto dto = service1.findClubByIdx(1L);
        String arr = dto.getClubGuest();
        System.out.println(dto.getClubGuest());
        List<Long> list = PublicMethod.stringToLongArr(arr);
        for(Long a : list){
            System.out.println(a);
        }
        return "";
    }
}
