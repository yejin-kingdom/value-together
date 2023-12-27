package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.reponse.CreateTeamRes;
import com.vt.valuetogether.domain.team.dto.request.CreateTeamReq;
import com.vt.valuetogether.domain.team.entity.Role;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;

    public CreateTeamRes createTeam(CreateTeamReq req, User user) {
        Team team =
                Team.builder()
                        .teamName(req.getTeamName())
                        .teamDescription(req.getTeamDescription())
                        .build();

        TeamRole teamRole = TeamRole.builder().user(user).team(team).role(Role.LEADER).build();

        teamRoleRepository.save(teamRole);

        team.addTeamRole(teamRole);

        return TeamServiceMapper.INSTANCE.toCreateTeamRes(teamRepository.save(team));
    }

    @Mapper
    public interface TeamServiceMapper {

        TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

        CreateTeamRes toCreateTeamRes(Team team);
    }
}
