package com.vt.valuetogether.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskSaveRes {
    private Long taskId;

    @Builder
    private TaskSaveRes(Long taskId) {
        this.taskId = taskId;
    }
}
