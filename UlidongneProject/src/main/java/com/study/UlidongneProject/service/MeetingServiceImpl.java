package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.service.Interface.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final Service1 service1;
    private final ClubRepository clubRepository;
    @Override
    public void create() {

    }

    @Override
    public void join() {

    }

    @Override
    public void quit() {

    }

    @Override
    public void memberJoin() {

    }

    @Override
    public void memberQuit() {

    }

    public List<MeetingResponseDto> meetingFindAll(){

        List<MeetingResponseDto> dtoList = new ArrayList<>();
        try{
            List<MeetingEntity> entityList = meetingRepository.findAll();
            if(entityList.size()>0) {
                for (MeetingEntity meetingEntity : entityList) {
                    if(meetingEntity.getMeetingDate().isAfter(LocalDate.now())) {
                        MeetingResponseDto dto = new MeetingResponseDto(meetingEntity, service1);
                        ClubEntity clubEntity = clubRepository.findById(dto.getMeetingClub()).get();
                        String clubImgUrl = clubEntity.getClubProfileImage();
                        String[] location = clubEntity.getClubLocation().split(" ");
                        dto.setMeetingLocation(location[location.length-1]);
                        dto.setClubName(clubEntity.getClubName());
                        dto.setClubImgUrl(clubImgUrl);
                        dto.setMeetingParticipants(meetingEntity.getMeetingAttend().split(",").length);
                        dto.setClubHost(clubEntity.getClubHost());
                        dto.setJoinCount(dto.getMeetingAttend().size());

                        if (dto.getMeetingAttend().size() > 3) {
                            List<MemberResponseDto> list = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                MemberResponseDto memberResponseDto = dto.getMeetingAttend().get(i);
                                list.add(memberResponseDto);
                            }
                            dto.setMeetingAttend(list);
                        }
                    dtoList.add(dto);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("오류");
        }

        return setImminentDay(dtoList);

    }
    public List<MeetingResponseDto> setImminentDay(List<MeetingResponseDto> meetingList) {
        //날짜를 계산해서 오늘,내일,모레를 설정해주는 함수 (해당사항 없으면 null)
        for (int i = 0; i < meetingList.size(); i++) {
            LocalDate meetingDate = meetingList.get(i).getMeetingDate();
            LocalDate now = LocalDate.now();
            // 두 날짜 간의 차이 계산
            long diffInDays = ChronoUnit.DAYS.between(now, meetingDate);
            if (diffInDays == 0l) {
                meetingList.get(i).setImminentDay("오늘");
            } else if (diffInDays == 1l){
                meetingList.get(i).setImminentDay("내일");
            } else if (diffInDays == 2l) {
                meetingList.get(i).setImminentDay("모레");
            }
        }
        return setDayOfWeek(meetingList);
    }

    public List<MeetingResponseDto> setDayOfWeek(List<MeetingResponseDto> meetingList) {
        //요일을 설정해주는 함수
        for (int i = 0; i < meetingList.size(); i++) {
            LocalDate meetingDate = meetingList.get(i).getMeetingDate();
            // 요일을 나타내는 열거형 상수(DayOfWeek)를 반환
            DayOfWeek dayOfWeek = meetingDate.getDayOfWeek();

            // 열거형 상수를 요일 문자열로 변환
            String dayOfWeekString = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);

            meetingList.get(i).setDayOfWeek(dayOfWeekString+"요일"); //요일

            // 월/일로 변경
            int month = meetingDate.getMonthValue();
            int day = meetingDate.getDayOfMonth();
            meetingList.get(i).setDayMonth(month + "월" + day + "일");
        }
        return meetingList;
    }
}
