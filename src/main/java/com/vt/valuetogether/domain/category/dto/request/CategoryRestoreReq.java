package com.vt.valuetogether.domain.category.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRestoreReq {
    private Long categoryId;
    private String username;

    @Builder
    private CategoryRestoreReq(Long categoryId, String username) {
        this.categoryId = categoryId;
        this.username = username;
    }
}
