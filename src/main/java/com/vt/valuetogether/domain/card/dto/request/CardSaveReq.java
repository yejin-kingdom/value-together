package com.vt.valuetogether.domain.card.dto.request;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardSaveReq {
    private Long categoryId;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private String username;

    @Builder
    private CardSaveReq(Long categoryId, String name, String description, LocalDateTime deadline) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }
}
