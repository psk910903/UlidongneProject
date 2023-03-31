package com.study.UlidongneProject.dto;

import javax.persistence.Column;
import java.time.LocalDate;

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
    private Long chattingIdx; //클럽 인덱스번호
    private String clubProfileImage;
    private String clubPhotos; // 배열
}
