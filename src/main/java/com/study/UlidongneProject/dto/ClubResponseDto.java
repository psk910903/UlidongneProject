package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ClubEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<Long> clubGuestLong;
    private String clubWaitGuest;
    private String clubCategory;
    private int clubLimit;
    private String clubIntroduce;
    private String clubContent;
    private String clubProfileImage;
    private LocalDate clubCreateDate = LocalDate.now();
    private int members;
    private String clubLo;


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
        this.clubProfileImage = entity.getClubProfileImage();
        this.clubCreateDate = entity.getClubCreateDate();
    }

    public ClubEntity toUpdateEntity(){
        return ClubEntity.builder()
                .clubIdx(clubIdx)
                .clubCategory(clubCategory)
                .clubContent(clubContent)
                .clubCreateDate(clubCreateDate)
                .clubGuest(clubGuest)
                .clubHost(clubHost)
                .clubIntroduce(clubIntroduce)
                .clubLimit(clubLimit)
                .clubLocation(clubLocation)
                .clubName(clubName)
                .clubProfileImage(clubProfileImage)
                .clubWaitGuest(clubWaitGuest)
                .build();
    }
}