package com.vt.valuetogether.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskDeleteReq {
    private Long taskId;

    @Builder
    private TaskDeleteReq(Long taskId) {
        this.taskId = taskId;
    }
}
