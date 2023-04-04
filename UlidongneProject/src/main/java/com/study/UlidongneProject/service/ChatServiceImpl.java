package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;
import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import com.study.UlidongneProject.entity.ChattingEntity;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.repository.ChattingRepository;
import com.study.UlidongneProject.service.Interface.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChattingRepository chattingRepository;

    @Override
    public List<ChattingResponseDto> findByClubIdx(Long clubIdx) {
        List<ChattingEntity> list = chattingRepository.findByClubIdx(clubIdx);
        return list.stream().map(ChattingResponseDto::new).collect(Collectors.toList());
    }

    public boolean save(ChattingSaveRequestDto dto) {

        ChattingEntity entity = dto.toSaveEntity();
        try {
            chattingRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
