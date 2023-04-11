package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.MemberEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    public String join(MemberSaveRequestDto dto);

    public void login();

    public void modify();

    public void quit();

    public void logout();

}
