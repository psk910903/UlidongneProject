package com.study.UlidongneProject.controller;


import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.service.Interface.ChatService;
import com.study.UlidongneProject.service.Service1;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @GetMapping("/apiChatRoom")
    public String makeChatRoom(){
        return "tmpAPIChatRoom";
    }


    private final ChatService chatService;
    private final Service3 service3;
    private final Service1 service1;


    @GetMapping("/testChat/{clubId}")
    public String chatRoom(@PathVariable("clubId") Long clubIdx, Model model){

        // 임시(멤버 정보 찾기)
        String username = "01012345678";
        MemberEntity memberEntity = service3.findByUserName(username);

        // 채팅 내용 찾기
        List<ChattingResponseDto> chattingList = chatService.findByClubIdx(clubIdx);

        // 클럽 정보
        ClubResponseDto clubResponseDto = service1.findClubByIdx(clubIdx);

        model.addAttribute("club", clubResponseDto);
        model.addAttribute("user", memberEntity);
        model.addAttribute("chattingList", chattingList);

        return "chattingRoom";
    }
}


