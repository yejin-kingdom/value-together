package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberInviteRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberInviteReq;

public interface TeamService {
    TeamCreateRes createTeam(TeamCreateReq req);

    TeamDeleteRes deleteTeam(TeamDeleteReq req);

    TeamEditRes editTeam(TeamEditReq req);

    TeamMemberInviteRes inviteMember(TeamMemberInviteReq req);

    TeamMemberInviteRes confirmEmail(String email, String code);
}
