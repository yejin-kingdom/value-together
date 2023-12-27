package com.vt.valuetogether.domain.user.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateUsernameReq {
    private String username;
    private Long userId;

    @Builder
    private UserUpdateUsernameReq(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }
}
