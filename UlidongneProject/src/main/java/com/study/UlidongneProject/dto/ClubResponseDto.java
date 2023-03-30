package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ClubEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponseDto {
    private Long clubIdx;
    private String clubName;
    private String clubLocation;
    private Long clubHost;
    private String clubGuest;
    private String clubWaitGuest;
    private String clubCategory;
    private int clubLimit;
    private String clubIntroduce;
    private String clubContent;
    private Long chattingIdx;
    private String clubProfileImage;
    private String clubPhotos;
    private LocalDate clubCreateDate = LocalDate.now();
    private int members;


    public ClubResponseDto(ClubEntity entity) {
        this.clubIdx = entity.getClubIdx();
        this.clubName = entity.getClubName();
        this.clubLocation = entity.getClubLocation();
        this.clubHost = entity.getClubHost();
        this.clubGuest = entity.getClubGuest();
        this.clubWaitGuest = entity.getClubWaitGuest();
        this.clubCategory = entity.getClubCategory();
        this.clubLimit = entity.getClubLimit();
        this.clubIntroduce = entity.getClubIntroduce();
        this.clubContent = entity.getClubContent();
        this.chattingIdx = entity.getChattingIdx();
        this.clubProfileImage = entity.getClubProfileImage();
        this.clubPhotos = entity.getClubPhotos();
        this.clubCreateDate = entity.getClubCreateDate();
    }
}
