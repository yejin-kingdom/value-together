package com.vt.valuetogether.domain.category.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveRes {
    private Long categoryId;

    @Builder
    private CategorySaveRes(Long categoryId) {
        this.categoryId = categoryId;
    }
}
