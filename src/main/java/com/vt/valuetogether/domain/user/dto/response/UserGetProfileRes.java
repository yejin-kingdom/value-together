package com.vt.valuetogether.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGetProfileRes {

    private Long userId;

    private String username;

    private String email;

    private String introduce;

    private String profileImageUrl;

    @Builder
    private UserGetProfileRes(
            Long userId, String username, String email, String introduce, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
    }
}
