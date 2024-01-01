package com.vt.valuetogether.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMemberDeleteReq {
    private Long teamId;
    private String memberName;
    private String username;

    @Builder
    private TeamMemberDeleteReq(Long teamId, String memberName) {
        this.teamId = teamId;
        this.memberName = memberName;
    }
}
