package com.vt.valuetogether.domain.card.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardChangeSequenceReq {

    private Long categoryId;
    private Long cardId;
    private Double preSequence;
    private Double postSequence;
    private String username;

    @Builder
    private CardChangeSequenceReq(
            Long categoryId, Long cardId, Double preSequence, Double postSequence) {
        this.categoryId = categoryId;
        this.cardId = cardId;
        this.preSequence = preSequence;
        this.postSequence = postSequence;
    }
}
