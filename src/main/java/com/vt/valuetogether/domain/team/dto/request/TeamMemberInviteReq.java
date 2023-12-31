package com.vt.valuetogether.domain.team.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMemberInviteReq {
    private Long teamId;
    private List<String> memberNameList;
    private String username;

    @Builder
    private TeamMemberInviteReq(Long teamId, List<String> memberNameList) {
        this.teamId = teamId;
        this.memberNameList = memberNameList;
    }
}
