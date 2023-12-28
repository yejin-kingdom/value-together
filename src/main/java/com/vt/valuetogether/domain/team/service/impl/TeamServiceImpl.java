package com.vt.valuetogether.domain.team.service.impl;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.entity.Role;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;
import com.vt.valuetogether.global.validator.TeamValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;
    private final UserRepository userRepository;

    @Override
    public TeamCreateRes createTeam(TeamCreateReq req) {
        TeamValidator.validate(req);

        Team findTeam = teamRepository.findByTeamName(req.getTeamName());
        TeamValidator.checkIsDuplicateTeamName(findTeam);

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        Team team =
            Team.builder()
                .teamName(req.getTeamName())
                .teamDescription(req.getTeamDescription())
                .backgroundColor(req.getBackgroundColor())
                .build();

        TeamRole teamRole = TeamRole.builder().user(user).team(team).role(Role.LEADER).build();

        teamRepository.save(team);

        teamRoleRepository.save(teamRole);

        return TeamServiceMapper.INSTANCE.toCreateTeamRes(team);
    }

    // team의 leader와 user가 일치할 경우에만 팀을 삭제할 수 있다.
    @Override
    public TeamDeleteRes deleteTeam(TeamDeleteReq req) {
        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        Team team = teamRepository.findByTeamId(req.getTeamId());
        TeamValidator.validate(team);

        team.getTeamRoleList().stream()
            .filter(t -> t.getRole() == Role.LEADER && t.getUser().equals(user))
            .findAny()
            .ifPresentOrElse(
                t -> {
                    teamRepository.save(
                        Team.builder()
                            .teamId(team.getTeamId())
                            .teamName(team.getTeamName())
                            .teamDescription(team.getTeamDescription())
                            .isDeleted(true)
                            .build());

                    teamRoleRepository.save(
                        TeamRole.builder()
                            .team(team)
                            .user(user)
                            .isDeleted(true)
                            .build());
                },

                () -> {
                    throw new GlobalException(ResultCode.FORBBIDEN_TEAM_LEADER);
                });

        return new TeamDeleteRes();
    }

    @Override
    public TeamEditRes editTeam(TeamEditReq req) {
        TeamValidator.validate(req);

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        Team team = teamRepository.findByTeamId(req.getTeamId());
        TeamValidator.validate(team);

        Team findTeam = teamRepository.findByTeamName(req.getTeamName());
        TeamValidator.checkIsDuplicateTeamName(findTeam);

        team.getTeamRoleList().stream()
            .filter(t -> t.getRole() == Role.LEADER && t.getUser().equals(user))
            .findAny()
            .ifPresentOrElse(
                t ->
                    teamRepository.save(
                        Team.builder()
                            .teamId(team.getTeamId())
                            .teamName(req.getTeamName())
                            .teamDescription(req.getTeamDescription())
                            .backgroundColor(req.getBackgroundColor())
                            .build()),
                () -> {
                    throw new GlobalException(ResultCode.FORBBIDEN_TEAM_LEADER);
                });

        return new TeamEditRes();
    }

    @Mapper
    public interface TeamServiceMapper {

        TeamServiceMapper INSTANCE = Mappers.getMapper(TeamServiceMapper.class);

        TeamCreateRes toCreateTeamRes(Team team);
    }
}
