package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.MemberServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingResponseDto {


    private final MemberServiceImpl memberService;

    private Long meetingIdx;
    private Long meetingClub;
    private String meetingTitle;
    private LocalDate meetingDate;
    private String meetingTime;
    private String meetingLocation;
    private String meetingLocationUrl;
    private String meetingPay;
    private int meetingLimit;
    private List<MemberResponseDto> meetingAttend;
    private String clubName;
    private String clubImgUrl;
    private int meetingParticipants;
    private String imminentDay;
    private String dayOfWeek;
    private String dayMonth;
    private Long clubHost;
    private int joinCount;
    private List<Long> meetingJoinList;
    private int meetingJoinListSize;


    public MeetingResponseDto(MeetingEntity entity, MemberServiceImpl memberService) {
        this.meetingIdx = entity.getMeetingIdx();
        this.memberService = memberService;
        this.meetingClub = entity.getMeetingClub();
        this.meetingTitle = entity.getMeetingTitle();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.meetingLimit = entity.getMeetingLimit();
        this.meetingLocation = entity.getMeetingLocation();
        this.meetingLocationUrl = entity.getMeetingLocationUrl();
        this.meetingPay = entity.getMeetingPay();
        this.meetingAttend = memberService.findMeetingMemberList(entity.getMeetingIdx());
        this.meetingJoinList = PublicMethod.stringToLongList(entity.getMeetingAttend());
        this.meetingJoinListSize = PublicMethod.stringToLongList(entity.getMeetingAttend()).size();
    }

}
