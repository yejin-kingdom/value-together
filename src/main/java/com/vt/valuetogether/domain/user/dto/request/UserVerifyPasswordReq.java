package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyPasswordReq {
    private Long userId;
    private String password;

    @Builder
    private UserVerifyPasswordReq(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
