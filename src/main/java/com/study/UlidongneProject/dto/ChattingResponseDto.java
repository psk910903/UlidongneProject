package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ChattingEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChattingResponseDto {

    private Long chattingIdx;
    private Long clubIdx;
    private Long memberIdx;
    private String chattingType;
    private String chattingContent;
    private LocalDateTime chattingWriteTime;

    public ChattingResponseDto(ChattingEntity entity) {
        this.chattingIdx = entity.getChattingIdx();
        this.clubIdx = entity.getClubIdx();
        this.memberIdx = entity.getMemberIdx();
        this.chattingType = entity.getChattingType();
        this.chattingContent = entity.getChattingContent();
        this.chattingWriteTime = entity.getChattingWriteTime();
    }

}
