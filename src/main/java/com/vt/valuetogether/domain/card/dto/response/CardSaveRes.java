package com.vt.valuetogether.domain.card.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardSaveRes {
    private Long cardId;

    @Builder
    private CardSaveRes(Long cardId) {
        this.cardId = cardId;
    }
}
