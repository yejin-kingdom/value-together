package com.vt.valuetogether.domain.team.dto.reponse;

import com.vt.valuetogether.domain.team.entity.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMemberGetRes {
    private String username;
    private Role role;

    @Builder
    private TeamMemberGetRes(String username, Role role) {
        this.username = username;
        this.role = role;
    }
}
