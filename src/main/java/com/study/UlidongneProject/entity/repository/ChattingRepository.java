package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.ChattingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRepository extends JpaRepository<ChattingEntity, Long> {

    @Query(value = "SELECT * FROM chatting WHERE club_idx = :clubIdx order BY chatting_write_time", nativeQuery = true)
    List<ChattingEntity> findByClubIdx(@Param(value="clubIdx") Long clubIdx);

}
