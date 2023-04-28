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


    List<ClubEntity> findTop5ByClubCategory(String category);

    //최신순
    @Query(value = "SELECT * FROM club order BY club_idx DESC", nativeQuery = true)
    List<ClubEntity> clubOrderByDate();

    //최신순 1건
    @Query(value = "SELECT * FROM club order BY club_idx DESC LIMIT 1", nativeQuery = true)
    ClubEntity clubOrderByDateLimit1();

    //추천순(사람많은 순)
    @Query(value = "SELECT * FROM club order BY (LENGTH(club_guest) - LENGTH(REPLACE(club_guest,',',''))) / LENGTH(',') DESC", nativeQuery = true)
    List<ClubEntity> clubOrderByPeople();

    @Query(value = "SELECT * FROM club WHERE club_name LIKE CONCAT('%',:keyword,'%') AND club_location = :location ORDER BY (LENGTH(club_guest) - LENGTH(REPLACE(club_guest,',',''))) / LENGTH(',') DESC", nativeQuery = true)
    Page<ClubEntity> findByKeyword(@Param(value="keyword")String keyword, @Param(value = "location") String location, Pageable pageable);

    @Query(value = "SELECT * FROM club WHERE club_name LIKE CONCAT('%',:keyword,'%') AND club_location = :location AND club_category = :category ORDER BY (LENGTH(club_guest) - LENGTH(REPLACE(club_guest,',',''))) / LENGTH(',') DESC", nativeQuery = true)
    Page<ClubEntity> findByCategoryKeyword(@Param(value="category")String category, @Param(value = "location") String location, @Param(value="keyword")String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM club WHERE club_category = :category order BY club_create_date desc", nativeQuery = true)
    List<ClubEntity> findByCategory(@Param(value="category")String category);

    @Query(value = "SELECT * FROM club WHERE club_location = :location AND club_category = :category  ORDER BY (LENGTH(club_guest) - LENGTH(REPLACE(club_guest,',',''))) / LENGTH(',') DESC", nativeQuery = true)
    List<ClubEntity> findByClubCategoryLocation(@Param(value="category") String category, @Param(value="location") String location);
}
