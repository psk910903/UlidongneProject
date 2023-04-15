package com.study.UlidongneProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long memberIdx;
    @Column(name = "member_name")
    private String memberName;
    @Column(name = "member_phone")
    private String memberPhone;
    @Column(name = "member_birthday")
    private LocalDate memberBirthday;
    @Column(name = "member_gender")
    private String memberGender;
    @Column(name = "member_introduce")
    private String memberIntroduce;
    @Column(name = "member_picture")
    private String memberPicture;
    @Column(name = "member_location")
    private String memberLocation;
    @Column(name = "member_interest_case1")
    private String memberInterestCase1;
    @Column(name = "member_interest_case2")
    private String memberInterestCase2;
    @Column(name = "member_interest_case3")
    private String memberInterestCase3;
    @Column(name = "member_interest_case4")
    private String memberInterestCase4;
    @Column(name = "member_interest_case5")
    private String memberInterestCase5;
    @Column(name = "joined_club")
    private String joinedClub;
    @Column(name = "wait_club")
    private String waitClub;
    @Column(name = "member_role")
    private String memberRole;
    @Column(name = "member_joindate")
    private LocalDate memberJoindate = LocalDate.now();

}

