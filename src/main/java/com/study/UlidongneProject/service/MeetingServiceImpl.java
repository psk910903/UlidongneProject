package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.*;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MeetingRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Interface.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ClubRepository clubRepository;
    private final MemberServiceImpl memberService;
    @Override
    public boolean create(MeetingSaveRequestDto dto) {

        String time = dto.getMeetingTime().split(":")[0];
        String minute = dto.getMeetingTime().split(":")[1];
        if (minute.equals("0")) {
            dto.setMeetingTime(time + ":00");
        } else if (minute.length() == 1) {
            dto.setMeetingTime(time + ":0"+minute);
        }

        LocalDate date = PublicMethod.convertStringToLocalDate(dto.getMeetingDateStr());
        dto.setMeetingDate(date);

        String meetingLocationUrl = dto.getMeetingLocationUrl();
        if (meetingLocationUrl.equals("")) {
            dto.setMeetingLocationUrl(null);
        }

        MeetingEntity meetingEntity = dto.toSaveEntity();
        try {
            meetingRepository.save(meetingEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean quit(HashMap<String, String> data) {

        String meetingIdx = data.get("meetingIdx");
        String memberIdx = data.get("memberIdx");

        try {
            MeetingPatchDto meetingDto = new MeetingPatchDto(meetingRepository.findByMeetingIdx(meetingIdx));
            List<Long> meetingJoinMemberList = PublicMethod.stringToLongList(meetingDto.getMeetingAttend());
            meetingJoinMemberList.remove(Long.parseLong(memberIdx));
            meetingDto.setMeetingAttend(PublicMethod.longListToString(meetingJoinMemberList));
            meetingRepository.save(meetingDto.toUpdateEntity());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public int join(HashMap<String, String> data) {

        String meetingIdx = data.get("meetingIdx");
        String memberIdx = data.get("memberIdx");
        try {
            MeetingPatchDto meetingDto = new MeetingPatchDto(meetingRepository.findByMeetingIdx(meetingIdx));
            List<Long> meetingJoinMemberList = PublicMethod.stringToLongList(meetingDto.getMeetingAttend());
            if (meetingJoinMemberList.size() == meetingDto.getMeetingLimit()) {
                System.out.println("meetingJoinMemberList.size() = " + meetingJoinMemberList.size());
                System.out.println("meetingDto.getMeetingLimit() = " + meetingDto.getMeetingLimit());
                return -1;
            } else {
                meetingJoinMemberList.add(Long.parseLong(memberIdx));
                meetingDto.setMeetingAttend(PublicMethod.longListToString(meetingJoinMemberList));
                meetingRepository.save(meetingDto.toUpdateEntity());
                return 1;
            }
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }


    }

    public List<MeetingResponseDto> meetingFindAll(){

        List<MeetingResponseDto> dtoList = new ArrayList<>();
        try{
            List<MeetingEntity> entityList = meetingRepository.findAll();
            if(entityList.size()>0) {
                for (MeetingEntity meetingEntity : entityList) {
                    LocalTime time = PublicMethod.timeComparison(meetingEntity.getMeetingTime());

                    if(meetingEntity.getMeetingDate().isAfter(LocalDate.now()) ||
                            (meetingEntity.getMeetingDate().isEqual(LocalDate.now()) && time.isAfter(LocalTime.now()))) {

                        MeetingResponseDto dto = new MeetingResponseDto(meetingEntity, memberService);
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

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findMeetingByClubIdx(Long clubIdx){ // 클럽 pk값으로 미팅 찾기
        List<MeetingResponseDto> meetingDtoList = new ArrayList<>();
        try{
            List<MeetingEntity> meetingEntityList = meetingRepository.findByMeetingClub(clubIdx);
            if(meetingEntityList.size()>0) {
                for (MeetingEntity entity : meetingEntityList) {
                    LocalTime time = PublicMethod.timeComparisonTest(entity.getMeetingTime());
                    System.out.println("time = " + time);

                    if(entity.getMeetingDate().isAfter(LocalDate.now()) ||
                      (entity.getMeetingDate().isEqual(LocalDate.now()) && time.isAfter(LocalTime.now()))) {
                        meetingDtoList.add(new MeetingResponseDto(entity, memberService));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("일정 찾기 실패");
            System.out.println(e);
        }
        return meetingDtoList ;
    }
}
