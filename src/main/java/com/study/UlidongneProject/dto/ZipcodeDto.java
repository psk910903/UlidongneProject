package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.Zipcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZipcodeDto {
    private String ZIP_NO;
    private String SIDO;
    private String SIGUNGU;
    private String RI;
    private String DONG_NM;
    public ZipcodeDto(Zipcode entity) {
        this.ZIP_NO = entity.getZIP_NO();
        this.SIDO = entity.getSIDO();
        this.SIGUNGU = entity.getSIGUNGU();
        this.DONG_NM = entity.getDONG_NM();
        this.RI = entity.getRI();
    }
}
