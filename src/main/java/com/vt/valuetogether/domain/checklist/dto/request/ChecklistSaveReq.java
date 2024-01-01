package com.vt.valuetogether.domain.checklist.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistSaveReq {
    private Long cardId;
    private String title;
    private String username;

    @Builder
    private ChecklistSaveReq(Long cardId, String title) {
        this.cardId = cardId;
        this.title = title;
    }
}
