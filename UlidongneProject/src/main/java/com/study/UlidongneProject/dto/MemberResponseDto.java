package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MemberEntity;
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
    private LocalDate memberJoindate;

    public MemberResponseDto(MemberEntity entity) {
        this.memberIdx = entity.getMemberIdx();
        this.memberName = entity.getMemberName();
        this.memberPhone = entity.getMemberPhone();
        this.memberBirthday = entity.getMemberBirthday();
        this.memberGender = entity.getMemberGender();
        this.memberIntroduce = entity.getMemberIntroduce();
        this.memberPicture = entity.getMemberPicture();
        this.memberLocation = entity.getMemberLocation();
        this.memberInterestCase1 = entity.getMemberInterestCase1();
        this.memberInterestCase2 = entity.getMemberInterestCase2();
        this.memberInterestCase3 = entity.getMemberInterestCase3();
        this.memberInterestCase4 = entity.getMemberInterestCase4();
        this.memberInterestCase5 = entity.getMemberInterestCase5();
        this.joinedClub = entity.getJoinedClub();
        this.waitClub = entity.getWaitClub();
        this.memberRole = entity.getMemberRole();
        this.memberJoindate = entity.getMemberJoindate();
    }
}
