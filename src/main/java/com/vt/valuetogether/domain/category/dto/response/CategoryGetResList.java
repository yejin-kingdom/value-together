package com.vt.valuetogether.domain.category.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryGetResList {
    private List<CategoryGetRes> categories;
    private int total;

    @Builder
    private CategoryGetResList(List<CategoryGetRes> categories, int total) {
        this.categories = categories;
        this.total = total;
    }
}
