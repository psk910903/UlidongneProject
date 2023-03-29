package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.service.Service1;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingResponseDto {

    private final Service1 service1;

    private Long meetingIdx;
    private Long meetingClub;
    private String meetingTitle;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private String meetingEndTime;
    private String meetingLocation;
    private String meetingLocationUrl;
    private int meetingPay;
    private int meetingLimit;
    private List<MemberResponseDto> meetingAttend;


    public MeetingResponseDto(MeetingEntity entity, Service1 service1) {
        this.meetingIdx = entity.getMeetingIdx();
        this.service1 = service1;
        this.meetingClub = entity.getMeetingClub();
        this.meetingTitle = entity.getMeetingTitle();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.meetingEndTime = entity.getMeetingEndTime();
        this.meetingLocation = entity.getMeetingLocation();
        this.meetingLocationUrl = entity.getMeetingLocationUrl();
        this.meetingPay = entity.getMeetingPay();
        this.meetingAttend = service1.findMeetingMemberList(entity.getMeetingIdx());
    }
}
