package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MeetingResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Service1 {
    private final ClubRepository clubRepository;
    private final MeetingRepository meetingRepository;

    public ClubResponseDto findClubByIdx(Long idx){ // idx번호로 모임 찾기
        Club entity = new Club();
        try{
            entity = clubRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ClubResponseDto(entity) ;
    }

    public MeetingResponseDto findMeetingByClubIdx(Long idx){ // 클럽 번호로 미팅 찾기
        Meeting entity = new Meeting();
        try{
            entity = meetingRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return new MeetingResponseDto(entity) ;
    }

//    public List<MemberResponseDto> findClubMemberList(Long idx){ // 클럽 번호로 회원 리스트 찾기
//        List<MemberResponseDto> dtoList = new ArrayList<>();
//        Club clubEntity = clubRepository.findById(idx).get();
////        int memberIdxList[] = clubEntity.getClubGuest().
////        for(){
////
////        }
//    }



}
