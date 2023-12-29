package com.vt.valuetogether.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskGetRes {
    private Long taskId;
    private String content;
    private Boolean isCompleted;

    @Builder
    private TaskGetRes(Long taskId, String content, Boolean isCompleted) {
        this.taskId = taskId;
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
