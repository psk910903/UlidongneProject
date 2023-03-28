package com.study.UlidongneProject.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByMeetingClub(Long idx);
}
