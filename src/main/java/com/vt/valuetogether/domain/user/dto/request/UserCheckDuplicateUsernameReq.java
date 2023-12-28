package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckDuplicateUsernameReq {
    private String username;

    @Builder
    private UserCheckDuplicateUsernameReq(String username) {
        this.username = username;
    }
}
