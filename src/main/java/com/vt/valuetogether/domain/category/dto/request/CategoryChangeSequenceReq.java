package com.vt.valuetogether.domain.category.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryChangeSequenceReq {
    private Long categoryId;
    private Double preSequence;
    private Double postSequence;
    private String username;

    @Builder
    public CategoryChangeSequenceReq(Long categoryId, Double preSequence, Double postSequence) {
        this.categoryId = categoryId;
        this.preSequence = preSequence;
        this.postSequence = postSequence;
    }
}
