package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.NoticeResponseDto;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 확작성을 위해 반환형을 int 형식으로 바꿈
    // return: 0일 경우: ajax 통신 중 오류 발생(api.js에서 return 0)
    // return: 1일 경우: 일반적인 통신 성공
    // return: 2일 경우: 서버로 통신은 성공했지만 내부적인 오류가 생겼을 경우(일반적인 경우)
    // 그외 케이스를 나눌 경우 다른 숫자로 함수를 만들면 됨

    private final NoticeService noticeService;
    private final MemberServiceImpl memberService;
    private final CategoryService categoryService;
    private final MeetingServiceImpl meetingService;
    private final ClubServiceImpl clubService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) throws ParseException {
        String userPhone = user.getUsername(); //phone
        MemberEntity memberEntity = memberService.findByUserPhone(userPhone);

        List<CategoryEntity> category = categoryService.categoryFindAll();
        String[] location = memberEntity.getMemberLocation().split(" ");
        String locationStr = location[location.length-1]; // 회기1동

        List<MeetingResponseDto> meetingList = meetingService.meetingFindAll();
        List<ClubResponseDto> clubOrderByPeople = clubService.orderBy("people");
        List<ClubResponseDto> clubOrderByDate = clubService.orderBy("date");

        model.addAttribute("meetingList", meetingList);
        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", category);
        model.addAttribute("locationStr", locationStr);
        model.addAttribute("clubOrderByPeople", clubOrderByPeople);
        model.addAttribute("clubOrderByDate", clubOrderByDate);

        // 작성자 서호준
        NoticeResponseDto dto = noticeService.findRecentNotice();
        model.addAttribute("notice", dto);

        return "clubList/home";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model, HttpSession session) {
        return "loginForm"; }

    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

    @GetMapping("/join/personalInformation")
    public String joinPersonalInformation() {
        return "personalInformation";
    }

    @GetMapping("/personalInformation")
    public String PersonalInformation() {
        return "personalInformation";
    }

    @GetMapping("/config")
    public String configPage(){
        return "seeMore/setting/config";
    }

    @GetMapping("/center")
    public String customerCenter(){
        return "seeMore/setting/customerCenter";
    }
}
