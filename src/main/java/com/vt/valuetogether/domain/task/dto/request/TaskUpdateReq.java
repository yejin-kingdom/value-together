package com.vt.valuetogether.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskUpdateReq {
    private Long taskId;
    private String content;
    private Boolean isCompleted;

    @Builder
    private TaskUpdateReq(Long taskId, String content, Boolean isCompleted) {
        this.taskId = taskId;
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
