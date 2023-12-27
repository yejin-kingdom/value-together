package com.vt.valuetogether.domain.checklist.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistDeleteReq {
    private Long checklistId;

    @Builder
    private ChecklistDeleteReq(Long checklistId) {
        this.checklistId = checklistId;
    }
}
