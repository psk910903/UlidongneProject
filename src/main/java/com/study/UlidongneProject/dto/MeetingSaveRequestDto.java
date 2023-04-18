package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MeetingSaveRequestDto {

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



    public MeetingEntity toSaveEntity() {
        return MeetingEntity.builder()
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
}
