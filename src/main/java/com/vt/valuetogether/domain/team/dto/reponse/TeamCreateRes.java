package com.vt.valuetogether.domain.team.dto.reponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamCreateRes {

    private Long teamId;

    @Builder
    private TeamCreateRes(Long teamId) {
        this.teamId = teamId;
    }
}
