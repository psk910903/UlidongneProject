package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.CategoryResponseDto;
import com.study.UlidongneProject.entity.CategoryEntity;
import com.study.UlidongneProject.entity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findCategory() { // 모든 카테고리 찾기
        List<CategoryResponseDto> dtoList = new ArrayList<>();
        try {
            List<CategoryEntity> entityList = categoryRepository.findAll();
            for (CategoryEntity entity : entityList) {
                dtoList.add(new CategoryResponseDto(entity));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> categoryFindAll() {
        List<CategoryEntity> categoryList = new ArrayList<>();
        try {
            categoryList = categoryRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("오류발생");
        }
        return categoryList;
    }
}
