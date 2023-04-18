package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;

import java.util.List;

public interface ChatService {

    List<ChattingResponseDto> findByClubIdx(Long clubIdx);

    boolean save(ChattingSaveRequestDto dto);

    List<String> findMemberPictureByClubIdx(Long clubIdx);

    List<String> findMemberNameByClubIdx(Long clubIdx);
}
