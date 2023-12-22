package com.vt.valuetogether.domain.user.dto.response;

import com.vt.valuetogether.domain.user.entity.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRes {

    private Long userId;

    private String username;

    private String email;

    private Role role;

    @Builder
    public UserSignupRes(Long userId, String username, String email, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
