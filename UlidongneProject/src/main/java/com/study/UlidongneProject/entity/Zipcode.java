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
    private String SIGUNGU;
    private String EUPMYUN;
    private String DORO_CD;
    private String DORO;
    private String BUILD_NM;
    private String RI;
    private String H_DONG_NM;

}
