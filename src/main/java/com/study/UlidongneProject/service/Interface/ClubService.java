package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.ClubSaveRequestDto;
import org.springframework.security.core.userdetails.User;

public interface ClubService {
    boolean create(ClubSaveRequestDto dto, User user);

    boolean requestToJoin(Long clubIdx, Long memberIdx);

    boolean rejectToJoin(Long clubIdx, Long memberIdx);

    boolean join(Long clubIdx, Long memberIdx);

    int quit(Long clubIdx, Long memberIdx);

    public void modify();

    public void chatting();

    public void kick();
}
