package com.vt.valuetogether.domain.card.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardDeleteReq {
    private Long cardId;
    private String username;

    @Builder
    private CardDeleteReq(Long cardId) {
        this.cardId = cardId;
    }
}
