package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateProfileReq {
    private String username;
    private String password;
    private String introduce;

    @Builder
    private UserUpdateProfileReq(String username, String password, String introduce) {
        this.username = username;
        this.password = password;
        this.introduce = introduce;
    }
}
