package com.vt.valuetogether.domain.category.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveReq {
    private Long teamId;
    private String name;
    private String username;

    @Builder
    private CategorySaveReq(Long teamId, String name, String username) {
        this.teamId = teamId;
        this.name = name;
        this.username = username;
    }
}
