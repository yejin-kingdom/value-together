package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePasswordReq {
    private String password;
    private String newPassword;
    private Long userId;

    @Builder
    private UserUpdatePasswordReq(String password, String newPassword, Long userId) {
        this.password = password;
        this.newPassword = newPassword;
        this.userId = userId;
    }
}
