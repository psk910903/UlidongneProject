package com.study.UlidongneProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="zipcode")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Zipcode {
    @Id
    private String ZIP_NO;
    private String SIDO;
    private String SIDO_ENG;
    private String SIGUNGU;
    private String SIGUNGU_ENG;
    private String EUPMYUN;
    private String EUPMYUN_ENG;
    private String DORO_CD;
//    private String DORO;
//    private String DORO_ENG;
//    private String ZIP_NO;
//    private String SIDO;
//    private String ZIP_NO;
//    private String SIDO;
//    private String ZIP_NO;
//    private String SIDO;
//    private String ZIP_NO;
//    private String SIDO;
}
