package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class Controller3 {
    private final HttpSession httpSession;
    private final Service3 service3;
    private final ClubServiceImpl clubService;
    private final MeetingServiceImpl meetingService;
    private final AwsS3Service awsS3Service;
    private final MeetingRepository meetingRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) throws ParseException {
        String username = user.getUsername();

        MemberEntity memberEntity = service3.findByUserName(username);
        System.out.println(memberEntity.getMemberName());

        List<CategoryEntity> category = service3.categoryFindAll();
        String[] location = memberEntity.getMemberLocation().split(" ");
        String locationStr = location[location.length - 1]; // 회기1동

        List<MeetingResponseDto> meetingList = meetingService.meetingFindAll();
        List<ClubResponseDto> clubOrderByPeople = clubService.orderBy("people");
        List<ClubResponseDto> clubOrderByDate = clubService.orderBy("date");

        for (int i = 0; i < meetingList.size(); i++) {
            MeetingResponseDto meetingResponseDto = meetingList.get(i);
            System.out.println("meetingResponseDto = " + meetingResponseDto);
        }

        model.addAttribute("meetingList", meetingList);
        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", category);
        model.addAttribute("locationStr", locationStr);
        model.addAttribute("clubOrderByPeople", clubOrderByPeople);
        model.addAttribute("clubOrderByDate", clubOrderByDate);
        return "/clubList/home";
    }

    @GetMapping("/club")
    public String club(@AuthenticationPrincipal User user,Model model) {
        String username = user.getUsername();
        List<CategoryEntity> category = service3.categoryFindAll();
        MemberEntity memberEntity = service3.findByUserName(username);
        model.addAttribute("dto", memberEntity);
        model.addAttribute("categoryList", category);
        return "/clubList/makeClub";
    }

    @ResponseBody
    @PostMapping("/club")
    public Boolean save(ClubSaveRequestDto dto) {

        String url = awsS3Service.upload(dto.getFile());

        new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(url).
                build(), HttpStatus.OK);

        dto.setClubProfileImage(url);

        boolean result = clubService.save(dto);

        if (result == true) { // 등록 성공하면
//        if (true) { // 등록 성공하면
            return true;
        } else { // 등록 실패하면
            return false;
        }
    }

    @GetMapping("/club/{param}/meeting")
    public String meetingForm(@AuthenticationPrincipal User user, @PathVariable("param") Long clubIdx, Model model) {
        String username = user.getUsername();
        System.out.println("username = " + username);
        MemberEntity memberEntity = service3.findByUserName(username);
        model.addAttribute("clubIdx", clubIdx);
        model.addAttribute("memberDto", memberEntity);
        return "/clubContent/makeMeeting";
    }

    @ResponseBody
    @PostMapping("/meeting")
    public Boolean meeting(MeetingSaveRequestDto dto) {

        LocalDate date = service3.convertStringToLocalDate(dto.getMeetingDateStr());
        dto.setMeetingDate(date);

        String meetingLocationUrl = dto.getMeetingLocationUrl();
        if (meetingLocationUrl.equals("")) {
            dto.setMeetingLocationUrl(null);
        }
        System.out.println("meetingSaveRequestDto = " + dto);
        MeetingEntity meetingEntity = dto.toSaveEntity();
        meetingRepository.save(meetingEntity);
        if (true) { // 등록 성공하면
//        if (true) { // 등록 성공하면
            return true;
        } else { // 등록 실패하면
            return false;
        }
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm"; //loginForm.html로 응답
    }
}
