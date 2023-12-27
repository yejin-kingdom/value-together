package com.vt.valuetogether.domain.team.dto.reponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamCreateRes {

    private Long teamId;
    private String teamName;
    private String teamDescription;

    @Builder
    private TeamCreateRes(Long teamId, String teamName, String teamDescription) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }
}
