package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    NoticeEntity findFirstByOrderByNoticeCreatedDateDesc();

    @Query(value = "SELECT * FROM notice ORDER BY notice_createddate desc LIMIT 1;", nativeQuery = true)
    NoticeEntity findNotice();
}
