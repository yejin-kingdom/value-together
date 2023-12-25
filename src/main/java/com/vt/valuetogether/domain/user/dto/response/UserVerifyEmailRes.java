package com.vt.valuetogether.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyEmailRes {

    private String email;

    private final String message = "이메일에서 인증을 완료해주세요";

    @Builder
    private UserVerifyEmailRes(String email) {
        this.email = email;
    }
}
