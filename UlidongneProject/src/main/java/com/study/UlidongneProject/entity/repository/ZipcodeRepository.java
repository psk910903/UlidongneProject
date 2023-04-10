package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.Zipcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipcodeRepository extends JpaRepository<Zipcode, String> {
    @Query(value = "SELECT * FROM new_zip WHERE RI LIKE CONCAT(:keyword,'%') GROUP BY SIGUNGU, RI " +
            "UNION ALL " +
            "SELECT * FROM new_zip WHERE DONG_NM LIKE CONCAT(:keyword,'%') GROUP BY SIGUNGU, DONG_NM", nativeQuery = true)
    List<Zipcode> findByKeyword(String keyword);
}
