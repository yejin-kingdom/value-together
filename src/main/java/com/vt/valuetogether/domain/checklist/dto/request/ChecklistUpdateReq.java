package com.vt.valuetogether.domain.checklist.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistUpdateReq {
    private Long checklistId;
    private String title;

    @Builder
    private ChecklistUpdateReq(Long checklistId, String title) {
        this.checklistId = checklistId;
        this.title = title;
    }
}
