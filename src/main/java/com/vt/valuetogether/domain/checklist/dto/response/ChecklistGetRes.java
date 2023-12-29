package com.vt.valuetogether.domain.checklist.dto.response;

import com.vt.valuetogether.domain.task.dto.response.TaskGetRes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistGetRes {
    private Long checklistId;
    private String title;
    private List<TaskGetRes> tasks;

    @Builder
    private ChecklistGetRes(Long checklistId, String title, List<TaskGetRes> tasks) {
        this.checklistId = checklistId;
        this.title = title;
        this.tasks = tasks;
    }
}
