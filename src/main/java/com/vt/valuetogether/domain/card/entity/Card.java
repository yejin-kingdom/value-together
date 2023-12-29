package com.vt.valuetogether.domain.card.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_card")
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String name;
    private String description;
    private String fileUrl;
    private Double sequence;
    private LocalDateTime deadline;

    // TODO FIX Category
    private Long categoryId;

    @Builder
    private Card(
            Long cardId,
            String name,
            String description,
            String fileUrl,
            Double sequence,
            LocalDateTime deadline,
            Long categoryId) {
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.fileUrl = fileUrl;
        this.sequence = sequence;
        this.deadline = deadline;
        this.categoryId = categoryId;
    }
}
