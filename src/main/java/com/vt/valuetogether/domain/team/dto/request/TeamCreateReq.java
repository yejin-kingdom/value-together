package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamCreateReq {

    private String teamName;
    private String teamDescription;
    private String backgroundColor;
    private String username;

    @Builder
    private TeamCreateReq(String teamName, String teamDescription, String backgroundColor) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.backgroundColor = backgroundColor;
    }
}
