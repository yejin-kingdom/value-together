package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.response.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.response.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.response.TeamMemberGetRes;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamServiceMapper {

    TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

    TeamCreateRes toTeamCreateRes(Team team);

    @Mapping(source = "user.username", target = "username")
    TeamMemberGetRes toTeamMemberGetRes(TeamRole teamRole);

    @Mapping(source = "teamRoleList", target = "memberGetResList")
    TeamGetRes toTeamGetRes(Team team);
}
