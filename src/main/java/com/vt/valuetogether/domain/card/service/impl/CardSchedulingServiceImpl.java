package com.vt.valuetogether.domain.card.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.card.service.CardSchedulingService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CardSchedulingServiceImpl implements CardSchedulingService {
    private final CardRepository cardRepository;
    private final double ADD_SEQUENCE = 1.0;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void resetSequence() {
        List<Card> cards = cardRepository.findByOrderByCategoryCategoryIdAscSequenceAsc();
        Long prevCategoryId = cards.get(0).getCategory().getCategoryId();
        double sequence = 0.0;
        List<Card> newCards = new ArrayList<>();
        for (Card card : cards) {
            sequence = getNewSequence(sequence, prevCategoryId, card.getCategory().getCategoryId());
            newCards.add(
                    Card.builder()
                            .cardId(card.getCardId())
                            .name(card.getName())
                            .description(card.getDescription())
                            .fileUrl(card.getFileUrl())
                            .sequence(sequence)
                            .deadline(card.getDeadline())
                            .category(card.getCategory())
                            .build());
            prevCategoryId = card.getCategory().getCategoryId();
        }
        cardRepository.saveAll(newCards);
    }

    private double getNewSequence(double sequence, Long prevCategoryId, Long nowCategoryId) {
        if (!prevCategoryId.equals(nowCategoryId)) {
            return ADD_SEQUENCE;
        }
        return sequence + ADD_SEQUENCE;
    }
}
