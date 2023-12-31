package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.card.entity.Card;
import java.time.LocalDateTime;

public interface CardTest extends CategoryTest {

    Long TEST_CARD_ID = 1L;
    String TEST_NAME = "name";
    String TEST_DESCRIPTION = "description";
    String TEST_FILE_URL = "url";
    Double TEST_SEQUENCE = 1.0;
    LocalDateTime TEST_DEADLINE = LocalDateTime.now();

    String TEST_UPDATED_NAME = "updatedName";
    String TEST_UPDATED_DESCRIPTION = "updatedDescription";
    String TEST_UPDATED_FILE_URL = "updatedUrl";
    LocalDateTime TEST_UPDATED_DEADLINE = LocalDateTime.now().plusDays(1L);

    Long TEST_ANOTHER_CARD_ID = 2L;
    String TEST_ANOTHER_NAME = "anotherName";
    String TEST_ANOTHER_DESCRIPTION = "anotherDescription";
    String TEST_ANOTHER_FILE_URL = "anotherUrl";
    Double TEST_ANOTHER_SEQUENCE = 2.0;
    LocalDateTime TEST_ANOTHER_DEADLINE = LocalDateTime.now().plusDays(1L);

    Card TEST_CARD =
            Card.builder()
                    .cardId(TEST_CARD_ID)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .fileUrl(TEST_FILE_URL)
                    .sequence(TEST_SEQUENCE)
                    .deadline(TEST_DEADLINE)
                    .category(TEST_CATEGORY)
                    .build();

    Card TEST_UPDATED_CARD =
            Card.builder()
                    .cardId(TEST_CARD_ID)
                    .name(TEST_UPDATED_NAME)
                    .description(TEST_UPDATED_DESCRIPTION)
                    .fileUrl(TEST_UPDATED_FILE_URL)
                    .sequence(TEST_SEQUENCE)
                    .deadline(TEST_UPDATED_DEADLINE)
                    .category(TEST_CATEGORY)
                    .build();

    Card TEST_ANOTHER_CARD =
            Card.builder()
                    .cardId(TEST_ANOTHER_CARD_ID)
                    .name(TEST_ANOTHER_NAME)
                    .description(TEST_ANOTHER_DESCRIPTION)
                    .fileUrl(TEST_ANOTHER_FILE_URL)
                    .sequence(TEST_ANOTHER_SEQUENCE)
                    .deadline(TEST_ANOTHER_DEADLINE)
                    .category(TEST_CATEGORY)
                    .build();
}
