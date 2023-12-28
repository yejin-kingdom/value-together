package com.vt.valuetogether.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyPasswordRes {
    private boolean isMatched;

    @Builder
    private UserVerifyPasswordRes(boolean isMatched) {
        this.isMatched = isMatched;
    }
}
