package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyEmailReq {

    private String email;

    @Builder
    private UserVerifyEmailReq(String email) {
        this.email = email;
    }
}
