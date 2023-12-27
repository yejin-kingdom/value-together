package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TEAM;

import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.global.exception.GlobalException;

public class TeamValidator {
    public static void validate(Team team) {
        if (!checkIsNull(team)) {
            throw new GlobalException(NOT_FOUND_TEAM);
        }
    }

    private static boolean checkIsNull(Team team) {
        return team == null;
    }
}
