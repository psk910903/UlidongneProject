package com.study.UlidongneProject.entity.repository;

import com.study.UlidongneProject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    @Query(value = "SELECT * FROM member WHERE member_phone = :phone", nativeQuery = true)
    MemberEntity findByPhone(@Param(value="phone")String phone);

    @Query(value = "SELECT * FROM member WHERE member_phone = :phone", nativeQuery = true)
    Optional<MemberEntity> findUserByPhone(@Param(value="phone")String phone);

    @Query(value = "select * from member where member_name = :username", nativeQuery = true)
    Optional<MemberEntity> findByUserName(@Param("username") String username);
}
