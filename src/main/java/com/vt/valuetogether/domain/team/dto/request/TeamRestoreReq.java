package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamRestoreReq {
    private Long teamId;
    private String username;

    @Builder
    private TeamRestoreReq(Long teamId, String username) {
        this.teamId = teamId;
        this.username = username;
    }
}
