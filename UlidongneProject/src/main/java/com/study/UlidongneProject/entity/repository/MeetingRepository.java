package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.MeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    List<MeetingEntity> findByMeetingClub(Long idx);
}
