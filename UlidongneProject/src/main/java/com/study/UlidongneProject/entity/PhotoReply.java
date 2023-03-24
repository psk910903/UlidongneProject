package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Table(name = "photo_reply")
@Entity
public class PhotoReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_reply_idx")
    private Long photoReplyIdx;
    @Column(name = "photo_reply_content")
    private String photoReplyContent;
    @Column(name = "photo_idx")
    private Long photoIdx;
    @Column(name = "member_idx")
    private Long memberIdx;
    @Column(name = "photo_reply_datetime")
    private LocalDate photoReplyDatetime;
}
