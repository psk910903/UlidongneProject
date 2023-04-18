package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ClubEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class ClubSaveRequestDto {
    private Long clubIdx;
    private String clubName;
    private String clubLocation;
    private Long clubHost;
    private String clubGuest; // 배열
    private String clubWaitGuest; // 배열
    private String clubCategory;
    private int clubLimit;
    private String clubIntroduce;
    private String clubContent;
    private String clubProfileImage;
    private MultipartFile file;
    private LocalDate clubCreateDate;

    public ClubEntity toSaveEntity() {
        return ClubEntity.builder()
                .clubName(clubName)
                .clubLocation(clubLocation)
                .clubHost(clubHost)
                .clubGuest(clubGuest)
                .clubWaitGuest(clubWaitGuest)
                .clubCategory(clubCategory)
                .clubLimit(clubLimit)
                .clubIntroduce(clubIntroduce)
                .clubContent(clubContent)
                .clubProfileImage(clubProfileImage)
                .clubCreateDate(clubCreateDate)
                .build();
    }

    public ClubEntity toUpdateEntity() {
        return ClubEntity.builder()
                .clubIdx(clubIdx)
                .clubName(clubName)
                .clubLocation(clubLocation)
                .clubHost(clubHost)
                .clubGuest(clubGuest)
                .clubWaitGuest(clubWaitGuest)
                .clubCategory(clubCategory)
                .clubLimit(clubLimit)
                .clubIntroduce(clubIntroduce)
                .clubContent(clubContent)
                .clubProfileImage(clubProfileImage)
                .build();
    }

    @Override
    public String toString() {
        return "ClubSaveRequestDto{" +
                "clubIdx=" + clubIdx +
                ", clubName='" + clubName + '\'' +
                ", clubLocation='" + clubLocation + '\'' +
                ", clubHost=" + clubHost +
                ", clubGuest='" + clubGuest + '\'' +
                ", clubWaitGuest='" + clubWaitGuest + '\'' +
                ", clubCategory='" + clubCategory + '\'' +
                ", clubLimit=" + clubLimit +
                ", clubIntroduce='" + clubIntroduce + '\'' +
                ", clubContent='" + clubContent + '\'' +
                ", clubProfileImage='" + clubProfileImage + '\'' +
                '}';
    }
}
