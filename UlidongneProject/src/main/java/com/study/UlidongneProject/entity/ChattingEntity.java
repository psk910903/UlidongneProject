package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "chatting_log")
@Getter
public class ChattingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_log_idx")
    private Long chattingLogIdx;
    @Column(name = "chatting_idx")
    private String chattingIdx;
    @Column(name = "member_idx")
    private String memberIdx;
    @Column(name = "chatting_content_type")
    private String chattingContentType;
    @Column(name = "chatting_content")
    private String chattingContent;
    @Column(name = "chatting_createddate")
    private LocalDate chattingCreatedDate = LocalDate.now();
}
