package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.MeetingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private Long memberIdx;
    private String memberName;
    private String memberPhone;
    private LocalDate memberBirthday;
    private String memberBirthdayStr;
    private String memberGender;
    private String memberIntroduce;
    private String memberPicture;
    private String memberLocation;
    private String memberInterestCase1;
    private String memberInterestCase2;
    private String memberInterestCase3;
    private String memberInterestCase4;
    private String memberInterestCase5;
    private String joinedClub;
    private String waitClub;
    private String memberRole;
    private LocalDate memberJoinDate;
    private MultipartFile file;

    public MemberEntity toSaveEntity(String memberName, String memberPhone, LocalDate memberBirthday, String memberGender, String memberIntroduce, String memberPicture, String memberLocation, String memberInterestCase1, String memberInterestCase2, String memberInterestCase3, String memberInterestCase4, String memberInterestCase5, String joinedClub, String waitClub, String memberRole, LocalDate memberJoinDate) {
        return MemberEntity.builder()
                .memberName(memberName)
                .memberPhone(memberPhone)
                .memberBirthday(memberBirthday)
                .memberGender(memberGender)
                .memberIntroduce(memberIntroduce)
                .memberPicture(memberPicture)
                .memberLocation(memberLocation)
                .memberInterestCase1(memberInterestCase1)
                .memberInterestCase2(memberInterestCase2)
                .memberInterestCase3(memberInterestCase3)
                .memberInterestCase4(memberInterestCase4)
                .memberInterestCase5(memberInterestCase5)
                .joinedClub(joinedClub)
                .waitClub(waitClub)
                .memberRole(memberRole)
                .build();
    }
    public MemberEntity toUpdateEntity(Long memberIdx, String memberName, String memberPhone, LocalDate memberBirthday, String memberGender, String memberIntroduce, String memberPicture, String memberLocation, String memberInterestCase1, String memberInterestCase2, String memberInterestCase3, String memberInterestCase4, String memberInterestCase5, String joinedClub, String waitClub, String memberRole, LocalDate memberJoinDate) {
        return MemberEntity.builder()
                .memberIdx(memberIdx)
                .memberName(memberName)
                .memberPhone(memberPhone)
                .memberBirthday(memberBirthday)
                .memberGender(memberGender)
                .memberIntroduce(memberIntroduce)
                .memberPicture(memberPicture)
                .memberLocation(memberLocation)
                .memberInterestCase1(memberInterestCase1)
                .memberInterestCase2(memberInterestCase2)
                .memberInterestCase3(memberInterestCase3)
                .memberInterestCase4(memberInterestCase4)
                .memberInterestCase5(memberInterestCase5)
                .joinedClub(joinedClub)
                .waitClub(waitClub)
                .memberRole(memberRole)
                .build();
    }

    @Override
    public String toString() {
        return "MemberSaveRequestDto{" +
                "memberIdx=" + memberIdx +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberBirthday=" + memberBirthday +
                ", memberGender='" + memberGender + '\'' +
                ", memberIntroduce='" + memberIntroduce + '\'' +
                ", memberPicture='" + memberPicture + '\'' +
                ", memberLocation='" + memberLocation + '\'' +
                ", memberInterestCase1='" + memberInterestCase1 + '\'' +
                ", memberInterestCase2='" + memberInterestCase2 + '\'' +
                ", memberInterestCase3='" + memberInterestCase3 + '\'' +
                ", memberInterestCase4='" + memberInterestCase4 + '\'' +
                ", memberInterestCase5='" + memberInterestCase5 + '\'' +
                ", joinedClub='" + joinedClub + '\'' +
                ", waitClub='" + waitClub + '\'' +
                ", memberRole='" + memberRole + '\'' +
                ", memberJoinDate=" + memberJoinDate +
                ", file=" + file +
                '}';
    }
}

