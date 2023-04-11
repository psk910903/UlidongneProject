package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import org.springframework.security.core.userdetails.User;

public interface ClubService {
    public boolean create(ClubSaveRequestDto dto, User user);

    public void join();

    public void quit();

    public void modify();

    public void chatting();

    public void kick();
}
