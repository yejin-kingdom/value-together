package com.vt.valuetogether.domain.category.dto.response;

import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryGetRes {
    private Long categoryId;
    private String name;
    private Double sequence;
    private Boolean isDeleted;
    private List<CardGetRes> cards;

    @Builder
    private CategoryGetRes(
            Long categoryId, String name, Double sequence, Boolean isDeleted, List<CardGetRes> cards) {
        this.categoryId = categoryId;
        this.name = name;
        this.sequence = sequence;
        this.isDeleted = isDeleted;
        this.cards = cards;
    }
}
