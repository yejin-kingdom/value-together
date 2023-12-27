package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDeleteReq {
    private String teamName;
    private String username;

    @Builder
    private TeamDeleteReq(String teamName) {
        this.teamName = teamName;
    }
}
