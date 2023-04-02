package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.Zipcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipcodeRepository extends JpaRepository<Zipcode, String> {
    @Query(value = "SELECT  ZIP_NO, DORO, SIDO, SIGUNGU, H_DONG_NM, RI, EUPMYUN FROM zipcode where" +
            " RI LIKE CONCAT('%',:keyword,'%')" +
            " OR H_DONG_NM LIKE CONCAT('%',:keyword,'%')" +
            " OR EUPMYUN LIKE CONCAT('%',:keyword,'%')" +
            " GROUP BY H_DONG_NM, RI, EUPMYUN  limit 5", nativeQuery = true)
    List<Zipcode> findByKeyword(String keyword);
}
