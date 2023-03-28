package com.study.UlidongneProject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
public class MemberResponseDto {
    private Long memberIdx;
    private String memberName;
    private String memberPhone;
    private LocalDate memberBirthday;
    private String memberGender;
    private String memberIntroduce;
    private String memberPicture;
    private String memberLocation;
    private String memberInterestCase1;
    private String memberInterestCase2;
    private String memberInterestCase3;
    private String memberInterestCase4;
    private String memberInterestCase5;
    private String interestedClub;
    private String joinedClub;
    private String waitClub;
    private String memberRole;
    private LocalDate memberJoindate = LocalDate.now();
}
