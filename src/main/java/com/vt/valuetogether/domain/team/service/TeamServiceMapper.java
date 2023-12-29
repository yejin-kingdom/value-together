package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberGetRes;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamServiceMapper {

    TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

    TeamCreateRes toCreateTeamRes(Team team);

    @Mapping(source = "user.username", target = "username")
    TeamMemberGetRes toTeamMemberGetRes(TeamRole teamRole);

    @Mapping(source = "teamRoleList", target = "memberGetResList")
    TeamGetRes toTeamGetRes(Team team);
}
