package com.study.UlidongneProject.entity;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "club")
@Getter
public class Club {
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
    private String clubGuest;
    @Column(name = "club_wait_guest")
    private String clubWaitGuest;
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
    private String clubPhotos;
    @Column(name = "club_createddate")
    private LocalDate clubCreatedDate = LocalDate.now();
}
