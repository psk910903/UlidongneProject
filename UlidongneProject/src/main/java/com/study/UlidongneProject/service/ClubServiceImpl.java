package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.service.Interface.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    @Override
    public void create() {

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
            String[] location = clubDto.getClubLocation().split(" ");
            clubDto.setClubLocation(location[location.length - 1]);
        }
        return clubList;
    }

    public boolean save(ClubSaveRequestDto dto) {

        dto.setClubCreateDate(LocalDate.now());
        ClubEntity entity = dto.toSaveEntity();
        try {
            clubRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
