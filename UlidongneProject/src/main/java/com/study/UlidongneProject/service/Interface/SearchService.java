package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ClubResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {

    public abstract Page<ClubResponseDto> findByKeyword(String keyword, int page);

    public abstract List location(String location);

    public abstract Page<ClubResponseDto> findByCategory(String category, String keyword, int page);
}
