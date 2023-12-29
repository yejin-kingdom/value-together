package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.FORBIDDEN_TEAM_ROLE;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TEAM_ROLE;

import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.exception.GlobalException;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class TeamRoleValidator {

    public static void validate(List<TeamRole> teamRoleList) {
        if (checkIsNull(teamRoleList)) {
            throw new GlobalException(NOT_FOUND_TEAM_ROLE);
        }
    }

    public static void checkIsTeamMember(List<TeamRole> teamRoleList, User user) {
        if (!teamRoleListContainsUser(teamRoleList, user)) {
            throw new GlobalException(FORBIDDEN_TEAM_ROLE);
        }
    }

    private static boolean checkIsNull(List<TeamRole> teamRoleList) {
        return CollectionUtils.isEmpty(teamRoleList);
    }

    private static boolean teamRoleListContainsUser(List<TeamRole> teamRoleList, User user) {
        return teamRoleList.stream()
                .filter(teamRole -> !teamRole.isDeleted())
                .anyMatch(teamRole -> teamRole.getUser().getUserId().equals(user.getUserId()));
    }
}
