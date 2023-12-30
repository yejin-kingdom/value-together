package com.vt.valuetogether.domain.card.repository;

import com.vt.valuetogether.domain.card.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Card.class, idClass = Long.class)
public interface CardRepository {
    Card save(Card card);

    @Query(
            value =
                    "select ifnull(max(c.sequence), 0) + 1.0 from Card c where c.category.categoryId=:categoryId")
    Double getMaxSequence(Long categoryId);

    Card findByCardId(Long cardId);

    void delete(Card card);

    List<Card> findByOrderByCategoryIdAscSequenceAsc();

    List<Card> saveAll(Iterable<Card> cards);
}
