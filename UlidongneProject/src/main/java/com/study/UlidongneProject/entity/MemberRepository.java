package com.study.UlidongneProject.entity;

import com.study.UlidongneProject.entity.Club;
import com.study.UlidongneProject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
}
