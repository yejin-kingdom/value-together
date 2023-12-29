package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamEditReq {
    private Long teamId;
    private String teamName;
    private String teamDescription;
    private String backgroundColor;
    private String username;

    @Builder
    private TeamEditReq(
            Long teamId, String teamName, String teamDescription, String backgroundColor) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.backgroundColor = backgroundColor;
    }
}
