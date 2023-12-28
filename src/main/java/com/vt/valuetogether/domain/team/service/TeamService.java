package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;

public interface TeamService {
    TeamCreateRes createTeam(TeamCreateReq req);

    TeamDeleteRes deleteTeam(TeamDeleteReq req);

    TeamEditRes editTeam(TeamEditReq req);
}
