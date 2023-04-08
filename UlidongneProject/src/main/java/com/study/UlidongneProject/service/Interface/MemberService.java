package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.MemberEntity;

public interface MemberService {

    public void join(MemberSaveRequestDto dto);

    public void login();

    public void modify();

    public void quit();

    public void logout();

}
