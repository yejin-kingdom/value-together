package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_BACKGROUNDCOLOR_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TEAM;

import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.global.exception.GlobalException;
import java.util.regex.Pattern;

public class TeamValidator {
    private static final String TEAM_BACKGROUNDCOLOR_REGEX = "^#[0-9]{5}$";

    public static void validate(Team team) {
        if (!checkIsNull(team)) {
            throw new GlobalException(NOT_FOUND_TEAM);
        }
    }

    public static void validate(TeamCreateReq req) {
        if (!checkIsValidateTeamBackgroundColor(req.getBackgroundColor())) {
            throw new GlobalException(INVALID_BACKGROUNDCOLOR_PATTERN);
        }
    }

    private static boolean checkIsNull(Team team) {
        return team == null;
    }

    private static boolean checkIsValidateTeamBackgroundColor(String backgroundColor) {
        return Pattern.matches(TEAM_BACKGROUNDCOLOR_REGEX, backgroundColor);
    }
}
