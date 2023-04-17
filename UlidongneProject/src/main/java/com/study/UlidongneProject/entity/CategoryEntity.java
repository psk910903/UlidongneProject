package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx")
    private Long categoryIdx;
    @Column(name = "category_main")
    private String categoryMain;
    @Column(name = "category_image")
    private String categoryImage;
}
