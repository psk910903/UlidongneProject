package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryIdx;
    private String categoryMain;
    private String categoryImage;

    public CategoryResponseDto(CategoryEntity entity) {
        this.categoryIdx = entity.getCategoryIdx();
        this.categoryMain = entity.getCategoryMain();
        this.categoryImage = entity.getCategoryImage();
    }
}
