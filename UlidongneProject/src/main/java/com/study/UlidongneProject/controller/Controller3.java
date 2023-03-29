package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.service.ClubServiceImpl;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class Controller3 {
    private final HttpSession httpSession;
    private final Service3 service3;
    private final ClubServiceImpl clubService;
    private final ClubRepository clubRepository;

    @GetMapping("/")
    public String home(Model model) {
        //String username = user.getUsername();
        //임시
        String username = "01012345678";

        MemberEntity memberEntity = service3.findById(username);
        List<CategoryEntity> categoryEntities = service3.categoryFindAll();
        String[] location = memberEntity.getMemberLocation().split(" ");
        String locationStr = location[2];

        List<ClubResponseDto> clubOrderByPeople = clubService.orderBy("people");
        List<ClubResponseDto> clubOrderByDate = clubService.orderBy("date");

        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", categoryEntities);
        model.addAttribute("locationStr", locationStr);
        model.addAttribute("clubOrderByPeople", clubOrderByPeople);
        model.addAttribute("clubOrderByDate", clubOrderByDate);
        return "/clubList/home";
    }
















//
//    @RequestMapping("/googleLoginSuccess")
//    @ResponseBody
//    public String googleLoginSuccess(Model model){
//        SessionUser user = (SessionUser)httpSession.getAttribute("user");
//        String userName = "";
//        String userEmail = "";
//        String userPicture = "";
//        if( user != null ){
//            userName = user.getName();
//            userEmail = user.getEmail();
//            userPicture = user.getPicture();
//
//            //member_security에 회원정보가 없다면,
//            //회원가입절차를 진행한다.
//        }
//        return "<script>alert('구글로그인 성공:"+ userName +"'); location.href='/';</script>";
//    }
//
//    @RequestMapping("/googleLoginFailure")
//    @ResponseBody
//    public String googleLoginFailure(){
//        return "<script>alert('구글로그인 실패'); history.back();</script>";
//    }
    //로그인, sns로그인-----------------------------------------------------------------


}
