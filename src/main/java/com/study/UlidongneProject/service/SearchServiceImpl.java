package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ZipcodeDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.Zipcode;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.ZipcodeRepository;
import com.study.UlidongneProject.service.Interface.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final ClubRepository clubRepository;
    @Override
    public Page<ClubResponseDto> findByKeyword(String keyword, String location, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ClubEntity> list = clubRepository.findByKeyword(keyword, location,  pageable);
        return list.map(ClubResponseDto::new);
    }

    @Override
    public List location(String location) {
        return null;
    }

    @Override
    public Page<ClubResponseDto> findByCategory(String category, String keyword, String location, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ClubEntity> list = clubRepository.findByCategoryKeyword(category, location, keyword, pageable);
        return list.map(ClubResponseDto::new);
    }

    private final ZipcodeRepository zipcodeRepository;

    @Transactional
    public List<ZipcodeDto> findLocation(String keyword){ // 위치 찾기
        List<ZipcodeDto> zipcodes = new ArrayList<>();
        try{
            for(Zipcode zipcode : zipcodeRepository.findByKeyword(keyword)){
                zipcodes.add(new ZipcodeDto(zipcode));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return zipcodes;
    }
}
