package com.study.UlidongneProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controller3 {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "스프링 시큐리티 웹앱입니다.";
    }
}
