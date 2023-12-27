package com.vt.valuetogether.domain.checklist.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistSaveReq {
    // TODO ADD cardId
    private String title;

    @Builder
    private ChecklistSaveReq(String title) {
        this.title = title;
    }
}
