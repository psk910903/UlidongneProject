package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Service2 {

    private final MemberRepository memberRepository;
//    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByIdxWOClubRepo(Long idx){ // pk값으로 맴버 찾기
        MemberEntity entity = null;
        try {
            entity = memberRepository.findById(idx).get();
        }catch (Exception e){
            System.out.println(e);
        }
        MemberResponseDto dto = new MemberResponseDto(entity);
        return dto;
    }
}
