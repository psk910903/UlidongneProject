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


    public ClubResponseDto(ClubEntity dto) {
        this.clubIdx = dto.getClubIdx();
        this.clubName = dto.getClubName();
        this.clubLocation = dto.getClubLocation();
        this.clubHost = dto.getClubHost();
        this.clubGuest = dto.getClubGuest();
        this.clubWaitGuest = dto.getClubWaitGuest();
        this.clubCategory = dto.getClubCategory();
        this.clubLimit = dto.getClubLimit();
        this.clubIntroduce = dto.getClubIntroduce();
        this.clubContent = dto.getClubContent();
        this.chattingIdx = dto.getChattingIdx();
        this.clubProfileImage = dto.getClubProfileImage();
        this.clubPhotos = dto.getClubPhotos();
        this.clubCreateDate = dto.getClubCreateDate();
    }
}
