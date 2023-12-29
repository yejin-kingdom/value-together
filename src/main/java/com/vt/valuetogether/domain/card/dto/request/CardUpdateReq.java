package com.vt.valuetogether.domain.card.dto.request;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardUpdateReq {
    private Long cardId;
    private String name;
    private String description;
    private LocalDateTime deadline;

    @Builder
    private CardUpdateReq(Long cardId, String name, String description, LocalDateTime deadline) {
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }
}
