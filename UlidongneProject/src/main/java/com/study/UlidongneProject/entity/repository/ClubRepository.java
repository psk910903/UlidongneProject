package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
}
