package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TEAM_ROLE;

import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.global.exception.GlobalException;
import java.util.List;

public class TeamRoleValidator {
    public static void validate(List<TeamRole> teamRoleList) {
        if (checkIsNull(teamRoleList)) {
            throw new GlobalException(NOT_FOUND_TEAM_ROLE);
        }
    }

    private static boolean checkIsNull(List<TeamRole> teamRoleList) {
        return teamRoleList.isEmpty();
    }
}
