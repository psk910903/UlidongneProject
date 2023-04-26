package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ClubResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {

    Page<ClubResponseDto> findByKeyword(String keyword, String location, int page);

    List location(String location);

    Page<ClubResponseDto> findByCategory(String category, String keyword, String location, int page);
}
