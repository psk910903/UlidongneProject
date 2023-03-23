package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.service.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class Controller3 {
    private final HttpSession httpSession;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "스프링 시큐리티 웹앱입니다.";

    }

    @RequestMapping("/googleLoginSuccess")
    @ResponseBody
    public String googleLoginSuccess(Model model){
        SessionUser user = (SessionUser)httpSession.getAttribute("user");
        String userName = "";
        String userEmail = "";
        String userPicture = "";
        if( user != null ){
            userName = user.getName();
            userEmail = user.getEmail();
            userPicture = user.getPicture();

            //member_security에 회원정보가 없다면,
            //회원가입절차를 진행한다.
        }
        return "<script>alert('구글로그인 성공:"+ userName +"'); location.href='/';</script>";
    }

    @RequestMapping("/googleLoginFailure")
    @ResponseBody
    public String googleLoginFailure(){
        return "<script>alert('구글로그인 실패'); history.back();</script>";
    }
    //로그인, sns로그인-----------------------------------------------------------------
}
