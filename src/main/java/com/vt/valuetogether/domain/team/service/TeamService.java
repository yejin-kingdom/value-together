package com.vt.valuetogether.domain.team.service;

import com.vt.valuetogether.domain.team.dto.reponse.CreateTeamRes;
import com.vt.valuetogether.domain.team.dto.request.CreateTeamReq;
import com.vt.valuetogether.domain.team.entity.Role;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;
    private final UserRepository userRepository;

    public CreateTeamRes createTeam(CreateTeamReq req) {
        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        Team team =
                Team.builder()
                        .teamName(req.getTeamName())
                        .teamDescription(req.getTeamDescription())
                        .build();

        TeamRole teamRole = TeamRole.builder().user(user).team(team).role(Role.LEADER).build();

        teamRepository.save(team);

        teamRoleRepository.save(teamRole);

        return TeamServiceMapper.INSTANCE.toCreateTeamRes(team);
    }

    @Mapper
    public interface TeamServiceMapper {

        TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

        CreateTeamRes toCreateTeamRes(Team team);
    }
}
