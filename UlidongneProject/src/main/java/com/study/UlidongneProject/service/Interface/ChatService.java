package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;

import java.util.List;

public interface ChatService {

    public List<ChattingResponseDto> findByClubIdx(Long clubIdx);

    public boolean save(ChattingSaveRequestDto dto);
}
