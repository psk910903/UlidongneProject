package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.service.Interface.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final ClubRepository clubRepository;
    @Override
    public Page<ClubResponseDto> findByKeyword(String keyword, int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<ClubEntity> list = clubRepository.findByKeyword(keyword, pageable);
        return list.map(ClubResponseDto::new);

    }

    @Override
    public List location(String location) {
        return null;
    }

    @Override
    public List category(String category) {
        return null;
    }
}
