package com.vt.valuetogether.domain.category.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveReq {
    private Long teamId;
    private String name;
    private String username;

    @Builder
    private CategorySaveReq(Long teamId, String name) {
        this.teamId = teamId;
        this.name = name;
    }
}
