package com.vt.valuetogether.domain.team.controller;

import com.vt.valuetogether.domain.team.dto.reponse.CreateTeamRes;
import com.vt.valuetogether.domain.team.dto.request.CreateTeamReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/teams")
    public RestResponse<CreateTeamRes> createTeam(@RequestBody CreateTeamReq req, User user) {
        req.setUsername(user.getUsername());
        return RestResponse.success(teamService.createTeam(req));
    }
}
