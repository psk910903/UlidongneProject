package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.ChattingEntity;
import com.study.UlidongneProject.entity.ClubEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChattingSaveRequestDto {

    private Long chattingIdx;
    private Long clubIdx;
    private Long memberIdx;
    private String chattingType;
    private String chattingContent;
    private LocalDateTime chattingWriteTime;

    @Builder
    public ChattingSaveRequestDto(Long chattingIdx, Long clubIdx, Long memberIdx, String chattingType, String chattingContent, LocalDateTime chattingWriteTime) {
        this.chattingIdx = chattingIdx;
        this.clubIdx = clubIdx;
        this.memberIdx = memberIdx;
        this.chattingType = chattingType;
        this.chattingContent = chattingContent;
        this.chattingWriteTime = chattingWriteTime;
    }

    public ChattingEntity toSaveEntity() {
        return ChattingEntity.builder()
                .clubIdx(clubIdx)
                .memberIdx(memberIdx)
                .chattingType(chattingType)
                .chattingContent(chattingContent)
                .chattingWriteTime(chattingWriteTime)
                .build();
    }
}
