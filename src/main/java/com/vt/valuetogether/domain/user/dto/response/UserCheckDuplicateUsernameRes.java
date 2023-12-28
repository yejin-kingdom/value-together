package com.vt.valuetogether.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckDuplicateUsernameRes {

    private boolean isDuplicated;

    @Builder
    private UserCheckDuplicateUsernameRes(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
