package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.card.entity.Card;
import java.time.LocalDateTime;

public interface CardTest {

    Long TEST_CARD_ID = 1L;

    String TEST_NAME = "name";
    String TEST_DESCRIPTION = "description";
    String TEST_FILE_URL = "url";
    Double TEST_SEQUENCE = 1.0;
    LocalDateTime TEST_DEADLINE = LocalDateTime.now();
    Long categoryId = 1L;

    Card TEST_CARD =
            Card.builder()
                    .cardId(TEST_CARD_ID)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .fileUrl(TEST_FILE_URL)
                    .sequence(TEST_SEQUENCE)
                    .deadline(TEST_DEADLINE)
                    .categoryId(categoryId)
                    .build();
}
