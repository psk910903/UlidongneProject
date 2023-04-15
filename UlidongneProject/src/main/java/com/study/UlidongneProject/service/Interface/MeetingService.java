package com.study.UlidongneProject.service.Interface;

import java.util.HashMap;

public interface MeetingService {

    public void create();

    public int join(HashMap<String, String> data);

    public boolean quit(HashMap<String, String> data);
}
