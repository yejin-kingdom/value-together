package com.vt.valuetogether.domain.category.dto.response;

import com.vt.valuetogether.domain.card.dto.response.CardInnerCategoryRes;
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
    private List<CardInnerCategoryRes> cards;

    @Builder
    private CategoryGetRes(
            Long categoryId,
            String name,
            Double sequence,
            Boolean isDeleted,
            List<CardInnerCategoryRes> cards) {
        this.categoryId = categoryId;
        this.name = name;
        this.sequence = sequence;
        this.isDeleted = isDeleted;
        this.cards = cards;
    }
}
