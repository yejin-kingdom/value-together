package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupReq {

    private String username;

    private String password;

    private String email;

    @Builder
    private UserSignupReq(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
