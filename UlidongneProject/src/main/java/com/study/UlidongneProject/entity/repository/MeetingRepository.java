package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MeetingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {

    List<MeetingEntity> findByMeetingClub(Long clubIdx);

    @Query(value = "SELECT * FROM meeting WHERE meeting_idx = :meetingIdx", nativeQuery = true)
    MeetingEntity findByMeetingIdx(@Param(value="meetingIdx")String meetingIdx);
}
