package com.vt.valuetogether.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "invite", timeToLive = 300)
public class InviteCode {

    @Id private String code;
    private Long userId;
    private Long teamId;

    @Builder
    private InviteCode(String code, Long userId, Long teamId) {
        this.code = code;
        this.userId = userId;
        this.teamId = teamId;
    }
}
