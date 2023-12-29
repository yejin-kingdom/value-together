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
    private String username;
    private String password;

    @Builder
    private UserVerifyPasswordReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
