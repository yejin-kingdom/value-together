package com.vt.valuetogether.domain.card.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardDeleteReq {
    private Long cardId;

    @Builder
    private CardDeleteReq(Long cardId) {
        this.cardId = cardId;
    }
}
