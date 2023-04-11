package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

//    NoticeEntity findTop1OrderByAsc();
}
