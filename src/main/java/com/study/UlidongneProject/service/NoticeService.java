package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.NoticeResponseDto;
import com.study.UlidongneProject.entity.NoticeEntity;
import com.study.UlidongneProject.entity.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<NoticeResponseDto> findNoticeList(){ // 공지사항 모두 찾기
        List<NoticeResponseDto> dtoList = new ArrayList<>();
        try {
            List<NoticeEntity> noticeList = noticeRepository.findAll();
            for(NoticeEntity a: noticeList){
                dtoList.add(new NoticeResponseDto(a));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto findNoticeByIdx( Long idx){ // 공지사항 하나 찾기
        NoticeResponseDto dto = null;
        try{
            dto = new NoticeResponseDto(noticeRepository.findById(idx).get());
        }catch (Exception e){
            System.out.println(e);
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto findRecentNotice(){ // 최신 공지 찾기
        NoticeResponseDto dto = null;
        try{
            dto = new NoticeResponseDto(noticeRepository.findFirstByOrderByNoticeCreatedDateDesc());
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("dto = " + dto);

        return dto;
    }
}
