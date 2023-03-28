package com.study.UlidongneProject.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    @Query(value = "SELECT * FROM member WHERE member_phone = :phone", nativeQuery = true)
    MemberEntity findByPhone(@Param(value="phone")String phone);
}
