package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "chatting")
public class ChattingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_idx")
    private Long chattingIdx;
    @Column(name = "chatting_name")
    private String chattingName;
    @Column(name = "chatting_createddate")
    private LocalDate chattingCreatedDate = LocalDate.now();
    @Column(name = "chatting_member")
    private String chattingMember;
}
