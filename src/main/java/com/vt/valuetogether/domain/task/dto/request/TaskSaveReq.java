package com.vt.valuetogether.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskSaveReq {
    private Long checklistId;
    private String content;
    private String username;

    @Builder
    private TaskSaveReq(Long checklistId, String content) {
        this.checklistId = checklistId;
        this.content = content;
    }
}
