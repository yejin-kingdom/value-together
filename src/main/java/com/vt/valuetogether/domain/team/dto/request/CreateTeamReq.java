package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTeamReq {

    private String teamName;
    private String teamDescription;

    @Builder
    private CreateTeamReq(String teamName, String teamDescription) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }
}
