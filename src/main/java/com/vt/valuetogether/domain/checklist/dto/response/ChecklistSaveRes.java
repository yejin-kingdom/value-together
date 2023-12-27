package com.vt.valuetogether.domain.checklist.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistSaveRes {
    private Long checklistId;

    @Builder
    private ChecklistSaveRes(Long checklistId) {
        this.checklistId = checklistId;
    }
}
