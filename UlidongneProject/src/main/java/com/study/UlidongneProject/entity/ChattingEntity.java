package com.study.UlidongneProject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chatting")
@Getter
@NoArgsConstructor
public class ChattingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_idx")
    private Long chattingIdx;
    @Column(name = "club_idx")
    private Long clubIdx;
    @Column(name = "member_idx")
    private Long memberIdx;
    @Column(name = "chatting_type")
    private String chattingType;
    @Column(name = "chatting_content")
    private String chattingContent;
    @Column(name = "chatting_write_time")
    private LocalDateTime chattingWriteTime = LocalDateTime.now();

    @Builder
    public ChattingEntity(Long chattingIdx, Long clubIdx, Long memberIdx, String chattingType, String chattingContent, LocalDateTime chattingWriteTime) {
        this.chattingIdx = chattingIdx;
        this.clubIdx = clubIdx;
        this.memberIdx = memberIdx;
        this.chattingType = chattingType;
        this.chattingContent = chattingContent;
        this.chattingWriteTime = chattingWriteTime;
    }
}
