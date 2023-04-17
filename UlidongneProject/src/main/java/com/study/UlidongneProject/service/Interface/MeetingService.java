package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.MeetingSaveRequestDto;

import java.util.HashMap;

public interface MeetingService {

    boolean create(MeetingSaveRequestDto dto);

    int join(HashMap<String, String> data);

    boolean quit(HashMap<String, String> data);
}
