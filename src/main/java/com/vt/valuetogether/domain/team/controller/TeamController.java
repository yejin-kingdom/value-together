package com.vt.valuetogether.domain.team.controller;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public RestResponse<TeamCreateRes> createTeam(@RequestBody TeamCreateReq req, User user) {
        req.setUsername(user.getUsername());
        return RestResponse.success(teamService.createTeam(req));
    }

    @DeleteMapping
    public RestResponse<TeamDeleteRes> deleteTeam(@RequestBody TeamDeleteReq req, User user) {
        req.setUsername(user.getUsername());
        return RestResponse.success(teamService.deleteTeam(req));
    }
}
