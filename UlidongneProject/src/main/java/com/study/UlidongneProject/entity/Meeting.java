package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "meeting")
@Getter
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_idx")
    private Long meeting_idx;
    @Column(name = "meeting_club")
    private Long meeting_club;
    @Column(name = "meeting_title")
    private String meetingTitle;
    @Column(name = "meeting_date")
    private LocalDate meetingDate;
    @Column(name = "meeting_time")
    private LocalTime meetingTime;
    @Column(name = "meeting_end_time")
    private String meetingEndTitle;
    @Column(name = "meeting_location")
    private String meetingLocation;
    @Column(name = "meeting_location_url")
    private String meetingLocationUrl;
    @Column(name = "meeting_pay")
    private int meetingPay;
    @Column(name = "meeting_limit")
    private int meetingLimit;
    @Column(name = "meeting_attend")
    private String meetingAttend;
}
