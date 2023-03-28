package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MeetingEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MeetingResponseDto {
    private Long meetingIdx;
    private Long meetingClub;
    private String meetingTitle;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private String meetingEndTitle;
    private String meetingLocation;
    private String meetingLocationUrl;
    private int meetingPay;
    private int meetingLimit;
    private String meetingAttend;

    public MeetingResponseDto(MeetingEntity entity) {
        this.meetingIdx = entity.getMeetingIdx();
        this.meetingClub = entity.getMeetingClub();
        this.meetingTitle = entity.getMeetingTitle();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.meetingEndTitle = entity.getMeetingEndTitle();
        this.meetingLocation = entity.getMeetingLocation();
        this.meetingLocationUrl = entity.getMeetingLocationUrl();
        this.meetingPay = entity.getMeetingPay();
        this.meetingLimit = entity.getMeetingLimit();
        this.meetingAttend = entity.getMeetingAttend();
    }
}
