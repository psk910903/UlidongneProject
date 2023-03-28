package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "club")
@Getter
public class ClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_idx")
    private Long clubIdx;
    @Column(name = "club_name")
    private String clubName;
    @Column(name = "club_location")
    private String clubLocation;
    @Column(name = "club_host")
    private Long clubHost;
    @Column(name = "club_guest")
    private String clubGuest; // 배열
    @Column(name = "club_wait_guest")
    private String clubWaitGuest; // 배열
    @Column(name = "club_category")
    private String clubCategory;
    @Column(name = "club_limit")
    private int clubLimit;
    @Column(name = "club_introduce")
    private String clubIntroduce;
    @Column(name = "club_content")
    private String clubContent;
    @Column(name = "chatting_idx")
    private Long chattingIdx;
    @Column(name = "club_profile_image")
    private String clubProfileImage;
    @Column(name = "club_photos")
    private String clubPhotos; // 배열
    @Column(name = "club_create_date")
    private LocalDate clubCreateDate = LocalDate.now();
}
