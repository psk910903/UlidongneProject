package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.service.*;
import com.study.UlidongneProject.service.Interface.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.study.UlidongneProject.service.Service3.convertStringToLocalDate;

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
    public String home(Model model) throws ParseException {
        //String username = user.getUsername();
        //임시
        String username = "01012345678";

        MemberEntity memberEntity = service3.findById(username);
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
    public String club(Model model) {
        String username = "01012345678";
        List<CategoryEntity> category = service3.categoryFindAll();
        MemberEntity memberEntity = service3.findById(username);
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
    public String meetingForm(@PathVariable("param") Long clubIdx, Model model) {
        String username = "01012345678";
        MemberEntity memberEntity = service3.findById(username);
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
}
