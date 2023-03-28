package com.study.UlidongneProject.entity;

import com.study.UlidongneProject.entity.Club;
import com.study.UlidongneProject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query(value = "SELECT * FROM member WHERE member_phone = :phone", nativeQuery = true)
    Member findByPhone(@Param(value="phone")String phone);
}
