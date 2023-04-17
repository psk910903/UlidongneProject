package com.study.UlidongneProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "new_zip")
@NoArgsConstructor
@Builder
public class Zipcode {
    @Id
    private String ZIP_NO;
    private String SIDO;
    private String SIGUNGU;
    private String DONG_NM;
    private String RI;
}
