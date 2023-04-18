package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MeetingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MeetingPatchDto {

    private Long meetingIdx;
    private Long meetingClub;
    private String meetingTitle;
    private LocalDate meetingDate;
    private String meetingDateStr;
    private String meetingTime;
    private String meetingLocation;
    private String meetingLocationUrl;
    private String meetingPay;
    private int meetingLimit;
    private String meetingAttend;

    public MeetingEntity toUpdateEntity() {
        return MeetingEntity.builder()
                .meetingIdx(meetingIdx)
                .meetingClub(meetingClub)
                .meetingTitle(meetingTitle)
                .meetingDate(meetingDate)
                .meetingTime(meetingTime)
                .meetingLocation(meetingLocation)
                .meetingLocationUrl(meetingLocationUrl)
                .meetingPay(meetingPay)
                .meetingLimit(meetingLimit)
                .meetingAttend(meetingAttend)
                .build();
    }

    public MeetingPatchDto(MeetingEntity entity) {

        this.meetingIdx = entity.getMeetingIdx();
        this.meetingClub = entity.getMeetingClub();
        this.meetingTitle = entity.getMeetingTitle();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.meetingLocation = entity.getMeetingLocation();
        this.meetingLocationUrl = entity.getMeetingLocationUrl();
        this.meetingPay = entity.getMeetingPay();
        this.meetingLimit = entity.getMeetingLimit();
        this.meetingAttend = entity.getMeetingAttend();
    }
}
