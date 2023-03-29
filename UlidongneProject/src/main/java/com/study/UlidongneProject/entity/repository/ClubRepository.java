package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    //최신순
    @Query(value = "SELECT * FROM club order BY club_create_date desc", nativeQuery = true)
    List<ClubEntity> clubOrderByDate();

    //추천순(사람많은 순)
    @Query(value = "SELECT * FROM club order BY LENGTH(club_guest) DESC", nativeQuery = true)
    List<ClubEntity> clubOrderByPeople();

}