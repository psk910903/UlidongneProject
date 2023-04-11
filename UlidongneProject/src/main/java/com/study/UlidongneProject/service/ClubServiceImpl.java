package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.Interface.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final Service3 service3;
    private final Service1 service1;
    private final MemberRepository memberRepository;

    @Override
    public boolean create(ClubSaveRequestDto dto, User user) {

        dto.setClubCreateDate(LocalDate.now());
        dto.setClubLocation(PublicMethod.location(dto.getClubLocation()));
        ClubEntity entity = dto.toSaveEntity();
        try {
            //클럽생성
            clubRepository.save(entity);
            //저장한 클럽 idx 가져와서 회원이 가입한 클럽에 추가
            Long clubIdx = clubRepository.clubOrderByDateLimit1().getClubIdx();
            MemberEntity memberEntity = service3.findByUserPhone(user.getUsername());
            MemberResponseDto memberDto = service1.findMemberByIdx(memberEntity.getMemberIdx());
            String joinedClub = memberDto.getJoinedClub();
            if (joinedClub.equals("{}")) {
                memberDto.setJoinedClub("{" + clubIdx + "}");
            } else {
                // "}" 삭제 후 가입한 인덱스 추가
                String substring = joinedClub.substring(0, joinedClub.length() - 1);
                memberDto.setJoinedClub(substring + "," + clubIdx + "}");
            }
            memberRepository.save(memberDto.toUpdateEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void join() {

    }

    @Override
    public void quit() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void chatting() {

    }

    @Override
    public void kick() {

    }

    public List<ClubResponseDto> orderBy(String orderBy) {
        List<ClubEntity> clubListEntity;
        if (orderBy.equals("people")) { // 회원많은순
            clubListEntity = clubRepository.clubOrderByPeople();
        }else{ // date 최신순
            clubListEntity = clubRepository.clubOrderByDate();
        }
        return settingClubLocation(clubListEntity);
    }

    public List<ClubResponseDto> settingClubLocation(List<ClubEntity> clubListEntity) {

        List<ClubResponseDto> clubList = clubListEntity.stream().map(ClubResponseDto::new).collect(Collectors.toList());
        for (ClubResponseDto clubDto : clubList) {
            clubDto.setMembers(clubDto.getClubGuest().split(",").length);
            clubDto.setClubLocation(PublicMethod.locationLastArray(clubDto.getClubLocation()));
        }
        return clubList;
    }
}
