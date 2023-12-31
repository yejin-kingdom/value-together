package com.vt.valuetogether.domain.category.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEditReq {
    private Long categoryId;
    private String name;
    private String username;

    @Builder
    private CategoryEditReq(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
