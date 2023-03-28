package com.study.UlidongneProject.service.Interface;

import java.util.List;

public interface SearchService {

    public abstract List findByKeyword(String keyword);

    public abstract List location(String location);

    public abstract List category(String category);
}
