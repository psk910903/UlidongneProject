package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.service.Service3;
import com.study.UlidongneProject.service.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class Controller3 {
    private final HttpSession httpSession;
    private final Service3 service3;

    @GetMapping("/")
    public String home(Model model) {
//        String username = user.getUsername();
        String username = "01012345678";
        MemberEntity memberEntity = service3.findById(username);
        List<CategoryEntity> categoryEntities = service3.categoryFindAll();

        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", categoryEntities);
        return "/clubList/home";
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
