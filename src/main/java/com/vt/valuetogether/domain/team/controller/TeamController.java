package com.vt.valuetogether.domain.team.controller;

import com.vt.valuetogether.domain.team.dto.reponse.CreateTeamRes;
import com.vt.valuetogether.domain.team.dto.request.CreateTeamReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    public RestResponse<CreateTeamRes> createTeam(CreateTeamReq req, User user) {
        return RestResponse.success(teamService.createTeam(req, user));
    }
}
