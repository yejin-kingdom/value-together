package com.vt.valuetogether.domain.team.controller;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public RestResponse<TeamCreateRes> createTeam(
            @RequestBody TeamCreateReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.createTeam(req));
    }

    @DeleteMapping
    public RestResponse<TeamDeleteRes> deleteTeam(
            @RequestBody TeamDeleteReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.deleteTeam(req));
    }

    @PatchMapping
    public RestResponse<TeamEditRes> editTeam(
            @RequestBody TeamEditReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.editTeam(req));
    }
}
