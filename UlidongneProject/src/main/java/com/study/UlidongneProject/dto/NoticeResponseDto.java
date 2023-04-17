package com.study.UlidongneProject.dto;

import com.study.UlidongneProject.entity.NoticeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class NoticeResponseDto {
    private Long noticeIdx;
    private String noticeTitle;
    private String noticeContent;
    private LocalDate noticeCreatedDate;

    @Builder
    public NoticeResponseDto(NoticeEntity entity) {
        this.noticeIdx = entity.getNoticeIdx();
        this.noticeTitle = entity.getNoticeTitle();
        this.noticeContent = entity.getNoticeContent();
        this.noticeCreatedDate = entity.getNoticeCreatedDate();
    }

    @Override
    public String toString() {
        return "NoticeResponseDto{" +
                "noticeIdx=" + noticeIdx +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeCreatedDate=" + noticeCreatedDate +
                '}';
    }
}
