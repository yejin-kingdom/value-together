package com.vt.valuetogether.domain.team.dto.reponse;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamGetRes {
    private String teamName;
    private String teamDescription;
    private String backgroundColor;
    private List<TeamMemberGetRes> memberGetResList = new ArrayList<>();

    @Builder
    private TeamGetRes(
            String teamName,
            String teamDescription,
            String backgroundColor,
            List<TeamMemberGetRes> memberGetResList) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.backgroundColor = backgroundColor;
        this.memberGetResList = memberGetResList;
    }
}
