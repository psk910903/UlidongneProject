package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.other.PublicMethod;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String joinedClub;
    private String waitClub;
    private String memberRole;
    private LocalDate memberJoindate;
    private List<ClubResponseDto> clubList; // 가입한 클럽 객체 리스트
    private ClubRepository clubRepository;
    private String locationLast;

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
        this.locationLast = entity.getMemberLocation().split(" ")[entity.getMemberLocation().split(" ").length-1];
    }

    @Autowired
    public void setClubRepository(ClubRepository clubRepository) { // 수정자 생성자 주입
        this.clubRepository = clubRepository;
    }

    public void arrToClubDto(MemberEntity entity){ // entity를 받아서 자신의 clubList 에 값을 넣음
        List<Long> memberJoinedClub = PublicMethod.stringToLongList( entity.getJoinedClub());
        List<ClubResponseDto> clubList = findClubListByMemberIdx(memberJoinedClub);
        this.clubList = clubList;
    }

    @Transactional(readOnly = true)
    public List<ClubResponseDto> findClubListByMemberIdx (List<Long> memberJoinedClub){ // 회원이 가입한 클럽 리스트 찾기
        List<ClubResponseDto> list = new ArrayList<>();
        setClubRepository(clubRepository);
        for(int i=0; i<memberJoinedClub.size(); i++) {
            try {
                ClubEntity clubEntity = clubRepository.findById(memberJoinedClub.get(i)).get();
                ClubResponseDto clubDto = new ClubResponseDto(clubEntity);
                clubDto.setClubLocation(PublicMethod.locationLastArray(clubDto.getClubLocation()));
                list.add(clubDto);
            } catch (Exception e){
                System.out.println("가입한 클럽 없음");
            }
        }
        return list;
    }

    public MemberEntity toUpdateEntity(){
        return MemberEntity.builder()
                .joinedClub(joinedClub)
                .memberBirthday(memberBirthday)
                .memberGender(memberGender)
                .memberIdx(memberIdx)
                .memberInterestCase1(memberInterestCase1)
                .memberInterestCase2(memberInterestCase2)
                .memberInterestCase3(memberInterestCase3)
                .memberInterestCase4(memberInterestCase4)
                .memberInterestCase5(memberInterestCase5)
                .memberIntroduce(memberIntroduce)
                .memberJoindate(memberJoindate)
                .memberLocation(memberLocation)
                .memberName(memberName)
                .waitClub(waitClub)
                .memberPhone(memberPhone)
                .memberPicture(memberPicture)
                .memberRole(memberRole)
                .build();
    }
}
