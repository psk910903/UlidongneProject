package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;

public interface ClubService {
    boolean create(ClubSaveRequestDto dto, User user);

    boolean requestToJoin(Long clubIdx, Long memberIdx);

    boolean rejectToJoin(Long clubIdx, Long memberIdx);

    boolean join(Long clubIdx, Long memberIdx);

    int quit(Long clubIdx, Long memberIdx);

    boolean modify(HttpServletRequest request, String url);

    public void chatting();

    public void kick();
}
