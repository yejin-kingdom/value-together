package com.vt.valuetogether.domain.team.service;

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

public interface TeamService {
    TeamCreateRes createTeam(TeamCreateReq req);

    TeamDeleteRes deleteTeam(TeamDeleteReq req);

    TeamEditRes editTeam(TeamEditReq req);

    TeamMemberInviteRes inviteMember(TeamMemberInviteReq req);

    TeamMemberDeleteRes deleteMember(TeamMemberDeleteReq req);

    TeamMemberInviteRes confirmEmail(String email, String code);

    TeamGetRes getTeamInfo(Long teamId, String username);
}
