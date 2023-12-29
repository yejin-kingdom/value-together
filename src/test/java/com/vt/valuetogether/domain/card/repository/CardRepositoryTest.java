package com.vt.valuetogether.domain.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.test.CardTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest implements CardTest {
    @Autowired private CardRepository cardRepository;

    @Test
    @DisplayName("card 저장 테스트")
    void card_저장() {
        // given

        // when
        Card card = cardRepository.save(TEST_CARD);

        // then
        assertThat(card.getName()).isEqualTo(TEST_NAME);
        assertThat(card.getDescription()).isEqualTo(TEST_DESCRIPTION);
        assertThat(card.getFileUrl()).isEqualTo(TEST_FILE_URL);
        assertThat(card.getSequence()).isEqualTo(TEST_SEQUENCE);
        assertThat(card.getDeadline()).isEqualTo(TEST_DEADLINE);
    }

    @Test
    @DisplayName("categoryId로 max sequence 조회 테스트")
    void categoryId_max_sequence_조회() {
        // given
        Long categoryId = 1L;
        Card saveCard = cardRepository.save(TEST_CARD);

        // when
        Double maxSequence = cardRepository.getMaxSequence(categoryId);

        // then
        assertThat(maxSequence).isEqualTo(saveCard.getSequence());
    }

    @Test
    @DisplayName("id로 card 조회 테스트")
    void id_card_조회() {
        // given
        Card saveCard = cardRepository.save(TEST_CARD);

        // when
        Card card = cardRepository.findByCardId(saveCard.getCardId());

        // then
        assertThat(card.getName()).isEqualTo(saveCard.getName());
        assertThat(card.getDescription()).isEqualTo(saveCard.getDescription());
        assertThat(card.getFileUrl()).isEqualTo(saveCard.getFileUrl());
        assertThat(card.getSequence()).isEqualTo(saveCard.getSequence());
        assertThat(card.getDeadline()).isEqualTo(saveCard.getDeadline());
    }

    @Test
    @DisplayName("card 삭제 테스트")
    void card_삭제() {
        // given
        cardRepository.save(TEST_CARD);

        // when
        cardRepository.delete(TEST_CARD);
        Card card = cardRepository.findByCardId(TEST_CARD_ID);

        // then
        assertThat(card).isNull();
    }
}