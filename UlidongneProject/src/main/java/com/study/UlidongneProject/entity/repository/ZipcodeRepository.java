package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.Zipcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipcodeRepository extends JpaRepository<Zipcode, String> {
    @Query(value = "SELECT * FROM zipcode where DORO LIKE CONCAT('%',:keyword,'%') limit 5", nativeQuery = true)
    List<Zipcode> findByKeyword(String keyword);
}
