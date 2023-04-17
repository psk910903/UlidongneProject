package com.study.UlidongneProject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "meeting")
@Getter
@NoArgsConstructor
@ToString
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_idx")
    private Long meetingIdx;
    @Column(name = "meeting_club")
    private Long meetingClub;
    @Column(name = "meeting_title")
    private String meetingTitle;
    @Column(name = "meeting_date")
    private LocalDate meetingDate;
    @Column(name = "meeting_time")
    private String meetingTime;
    @Column(name = "meeting_location")
    private String meetingLocation;
    @Column(name = "meeting_location_url")
    private String meetingLocationUrl;
    @Column(name = "meeting_pay")
    private String meetingPay;
    @Column(name = "meeting_limit")
    private int meetingLimit;
    @Column(name = "meeting_attend")
    private String meetingAttend;

    @Builder
    public MeetingEntity(Long meetingIdx, Long meetingClub, String meetingTitle, LocalDate meetingDate, String meetingTime, String meetingLocation, String meetingLocationUrl, String meetingPay, int meetingLimit, String meetingAttend) {
        this.meetingIdx = meetingIdx;
        this.meetingClub = meetingClub;
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingLocation = meetingLocation;
        this.meetingLocationUrl = meetingLocationUrl;
        this.meetingPay = meetingPay;
        this.meetingLimit = meetingLimit;
        this.meetingAttend = meetingAttend;
    }

}
