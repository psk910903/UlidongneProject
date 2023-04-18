package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Table(name = "notice")
@Entity
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_idx")
    private Long noticeIdx;
    @Column(name = "notice_title")
    private String noticeTitle;
    @Column(name = "notice_content")
    private String noticeContent;
    @Column(name = "notice_createddate")
    private LocalDate noticeCreatedDate;
}
