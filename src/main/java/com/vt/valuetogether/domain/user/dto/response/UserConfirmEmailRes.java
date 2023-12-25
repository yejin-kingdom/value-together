package com.vt.valuetogether.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserConfirmEmailRes {

    private String email;

    private final String message = "이메일 인증이 완료되었습니다.";

    @Builder
    private UserConfirmEmailRes(String email) {
        this.email = email;
    }
}
