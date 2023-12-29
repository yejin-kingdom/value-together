package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_CARD;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.global.exception.GlobalException;

public class CardValidator {
    public static void validate(Card card) {
        if (isNullCard(card)) {
            throw new GlobalException(NOT_FOUND_CARD);
        }
    }

    private static boolean isNullCard(Card card) {
        return card == null;
    }
}
