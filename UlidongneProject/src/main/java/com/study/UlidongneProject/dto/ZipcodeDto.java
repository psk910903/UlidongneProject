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
    private String EUPMYUN;
    private String DORO;
    private String RI;
    private String H_DONG_NM;
    public ZipcodeDto(Zipcode entity) {
        this.ZIP_NO = entity.getZIP_NO();
        this.SIDO = entity.getSIDO();
        this.SIGUNGU = entity.getSIGUNGU();
        this.EUPMYUN = entity.getEUPMYUN();
        this.DORO = entity.getDORO();
        this.RI = entity.getRI();
        this.H_DONG_NM = entity.getH_DONG_NM();
    }
}
