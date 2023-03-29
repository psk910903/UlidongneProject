package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.service.Interface.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public List<MemberResponseDto> findByKeyword(String keyword) {
        return null;
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
