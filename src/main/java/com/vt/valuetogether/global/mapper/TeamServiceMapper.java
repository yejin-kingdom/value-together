package com.vt.valuetogether.global.mapper;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamServiceMapper {

    TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

    TeamCreateRes toCreateTeamRes(Team team);
}
