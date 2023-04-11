package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.MeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {

    List<MeetingEntity> findByMeetingClub(Long idx);
}
