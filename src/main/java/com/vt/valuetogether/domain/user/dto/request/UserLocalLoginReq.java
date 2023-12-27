package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocalLoginReq {

    private String username;

    private String password;

    @Builder
    private UserLocalLoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
