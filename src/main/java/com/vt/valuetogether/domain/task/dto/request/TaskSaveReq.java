package com.vt.valuetogether.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskSaveReq {
    private Long checklistId;
    private String content;

    @Builder
    private TaskSaveReq(Long checklistId, String content) {
        this.checklistId = checklistId;
        this.content = content;
    }
}
