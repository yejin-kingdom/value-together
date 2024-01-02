package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.DUPLICATED_TEAM_NAME;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_BACKGROUND_COLOR_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_DELETED_TEAM;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TEAM;

import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.global.exception.GlobalException;
import java.util.regex.Pattern;

public class TeamValidator {
    private static final String TEAM_BACKGROUND_COLOR_REGEX =
            "^\\#(([0-9a-fA-F]){3}|([0-9a-fA-F]){6}|([0-9a-fA-F]){8})$";

    public static void validate(Team team) {
        // team이 존재하지 않거나 삭제된 경우 NOT_FOUND_TEAM Exception 발생
        if (checkIsNull(team) || team.isDeleted()) {
            throw new GlobalException(NOT_FOUND_TEAM);
        }
    }

    public static void validate(TeamCreateReq req) {
        if (!checkIsValidateTeamBackgroundColor(req.getBackgroundColor())) {
            throw new GlobalException(INVALID_BACKGROUND_COLOR_PATTERN);
        }
    }

    public static void validate(TeamEditReq req) {
        if (!checkIsValidateTeamBackgroundColor(req.getBackgroundColor())) {
            throw new GlobalException(INVALID_BACKGROUND_COLOR_PATTERN);
        }
    }

    public static void isSoftDeleted(Team team) {
        if (checkIsNull(team)) {
            throw new GlobalException(NOT_FOUND_TEAM);
        }
        if (!checkIsDeleted(team)) {
            throw new GlobalException(NOT_DELETED_TEAM);
        }
    }

    public static void checkIsDuplicateTeamName(Team team) {
        if (!checkIsNull(team)) {
            throw new GlobalException(DUPLICATED_TEAM_NAME);
        }
    }

    private static boolean checkIsNull(Team team) {
        return team == null;
    }

    private static boolean checkIsValidateTeamBackgroundColor(String backgroundColor) {
        return Pattern.matches(TEAM_BACKGROUND_COLOR_REGEX, backgroundColor);
    }

    private static boolean checkIsDeleted(Team team) {
        return team.isDeleted();
    }
}
