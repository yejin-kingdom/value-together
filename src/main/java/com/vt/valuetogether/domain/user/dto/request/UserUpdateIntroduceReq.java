package com.vt.valuetogether.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateIntroduceReq {
    private String introduce;
    private Long userId;

    @Builder
    private UserUpdateIntroduceReq(String introduce, Long userId) {
        this.introduce = introduce;
        this.userId = userId;
    }
}
