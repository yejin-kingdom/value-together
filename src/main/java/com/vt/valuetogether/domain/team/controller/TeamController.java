package com.vt.valuetogether.domain.team.controller;

import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberInviteReq;
import com.vt.valuetogether.domain.team.dto.response.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.response.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.response.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.response.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.response.TeamMemberDeleteRes;
import com.vt.valuetogether.domain.team.dto.response.TeamMemberInviteRes;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public RestResponse<TeamGetRes> getTeamInfo(
            @PathVariable Long teamId, @AuthenticationPrincipal UserDetails userDetails) {
        return RestResponse.success(teamService.getTeamInfo(teamId, userDetails.getUsername()));
    }

    @GetMapping("/members/email")
    public RestResponse<TeamMemberInviteRes> confirmEmail(
            @RequestParam(name = "email") String email, @RequestParam(name = "authCode") String code) {
        return RestResponse.success(teamService.confirmEmail(email, code));
    }

    @PostMapping
    public RestResponse<TeamCreateRes> createTeam(
            @RequestBody TeamCreateReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.createTeam(req));
    }

    @PostMapping("/members")
    public RestResponse<TeamMemberInviteRes> inviteMember(
            @RequestBody TeamMemberInviteReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.inviteMember(req));
    }

    @PatchMapping
    public RestResponse<TeamEditRes> editTeam(
            @RequestBody TeamEditReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.editTeam(req));
    }

    @DeleteMapping
    public RestResponse<TeamDeleteRes> deleteTeam(
            @RequestBody TeamDeleteReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.deleteTeam(req));
    }

    @DeleteMapping("/members")
    public RestResponse<TeamMemberDeleteRes> deleteTeamMember(
            @RequestBody TeamMemberDeleteReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(teamService.deleteMember(req));
    }
}
