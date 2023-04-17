package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    @GetMapping("/notice/list")
    public String noticeList(Model model){
        model.addAttribute("notice", noticeService.findNoticeList());
        return "seeMore/setting/notice";
    }

    @GetMapping("/notice/{noticeIdx}")
    public String notice(@PathVariable("noticeIdx") Long noticeIdx, Model model){
        model.addAttribute("notice", noticeService.findNoticeByIdx(noticeIdx));
        return "seeMore/setting/noticeContent";
    }
}
