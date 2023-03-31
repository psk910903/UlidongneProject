package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.service.ClubServiceImpl;
import com.study.UlidongneProject.service.Interface.MeetingService;
import com.study.UlidongneProject.service.MeetingServiceImpl;
import com.study.UlidongneProject.service.Service3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class Controller3 {
    private final HttpSession httpSession;
    private final Service3 service3;
    private final ClubServiceImpl clubService;
    private final MeetingServiceImpl meetingService;

    @GetMapping("/")
    public String home(Model model) throws ParseException {
        //String username = user.getUsername();
        //임시
        String username = "01012345678";

        MemberEntity memberEntity = service3.findById(username);
        List<CategoryEntity> categoryEntities = service3.categoryFindAll();
        String[] location = memberEntity.getMemberLocation().split(" ");
        String locationStr = location[2]; // 회기1동

        List<MeetingResponseDto> meetingList = meetingService.meetingFindAll();
        List<ClubResponseDto> clubOrderByPeople = clubService.orderBy("people");
        List<ClubResponseDto> clubOrderByDate = clubService.orderBy("date");

        model.addAttribute("meetingList", meetingList);
        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", categoryEntities);
        model.addAttribute("locationStr", locationStr);
        model.addAttribute("clubOrderByPeople", clubOrderByPeople);
        model.addAttribute("clubOrderByDate", clubOrderByDate);
        return "/clubList/home";
    }

    @GetMapping("/club")
    public String club() {
        return "/clubList/makeClub";
    }

    @ResponseBody
    @PostMapping("/club")
    public Boolean save(@RequestBody ClubSaveRequestDto clubSaveRequestDto) {



        if (true) { // 등록 성공하면
            return true;
        } else { // 등록 실패하면
            return false;
        }
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
