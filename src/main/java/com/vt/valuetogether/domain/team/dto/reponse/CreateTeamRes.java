package com.vt.valuetogether.domain.team.dto.reponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTeamRes {

    private Long teamId;
    private String teamName;
    private String teamDescription;

    @Builder
    private CreateTeamRes(Long teamId, String teamName, String teamDescription) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }
}
