package com.vt.valuetogether.domain.team.service.impl;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamEditRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberInviteRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamEditReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberInviteReq;
import com.vt.valuetogether.domain.team.entity.Role;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.domain.team.service.TeamServiceMapper;
import com.vt.valuetogether.domain.user.entity.InviteCode;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.InviteRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.user.service.InviteCodeService;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.TeamValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import com.vt.valuetogether.infra.mail.MailUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private static final String EMAIL_AUTHENTICATION = "이메일 인증";

    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;
    private final UserRepository userRepository;
    private final MailUtil mailUtil;
    private final InviteRepository inviteRepository;
    private final InviteCodeService inviteCodeService;

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

        Team saveTeam = teamRepository.save(team);

        teamRoleRepository.save(teamRole);

        return TeamServiceMapper.INSTANCE.toTeamCreateRes(saveTeam);
    }

    @Transactional
    @Override
    public TeamDeleteRes deleteTeam(TeamDeleteReq req) {
        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        Team team = teamRepository.findByTeamId(req.getTeamId());
        TeamValidator.validate(team);

        List<TeamRole> teamRoleList = teamRoleRepository.findByTeam_TeamId(team.getTeamId());
        TeamRoleValidator.validate(teamRoleList);

        team.getTeamRoleList().stream()
                .filter(t -> t.getRole() == Role.LEADER && t.getUser().equals(user))
                .findAny()
                .ifPresentOrElse(
                        t ->
                                teamRepository.save(
                                        Team.builder()
                                                .teamId(team.getTeamId())
                                                .teamName(team.getTeamName())
                                                .teamDescription(team.getTeamDescription())
                                                .isDeleted(true)
                                                .build()),
                        () -> {
                            throw new GlobalException(ResultCode.FORBIDDEN_TEAM_LEADER);
                        });

        return new TeamDeleteRes();
    }

    @Transactional
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
                            throw new GlobalException(ResultCode.FORBIDDEN_TEAM_LEADER);
                        });

        return new TeamEditRes();
    }

    @Override
    public TeamMemberInviteRes inviteMember(TeamMemberInviteReq req) {
        Team team = teamRepository.findByTeamId(req.getTeamId());
        TeamValidator.validate(team);

        List<TeamRole> teamRoleList = teamRoleRepository.findByTeam_TeamId(team.getTeamId());
        TeamRoleValidator.validate(teamRoleList);
        Map<Long, TeamRole> teamRoleByUserId =
                teamRoleList.stream()
                        .collect(
                                Collectors.toMap(teamRole -> teamRole.getUser().getUserId(), Function.identity()));

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        List<User> memberList = userRepository.findAllByUsernameIn(req.getMemberNameList());

        // 전달받은 username으로 조회한 memberList의 user가 이미 teamRoleList에 존재한다면 제외
        List<User> matchingMemberList =
                memberList.stream()
                        .filter(member -> !teamRoleByUserId.containsKey(member.getUserId()))
                        .collect(Collectors.toList());

        sendInviteMail(matchingMemberList, team.getTeamId());

        return new TeamMemberInviteRes();
    }

    private void sendInviteMail(List<User> matchingMemberList, Long teamId) {
        for (User m : matchingMemberList) {
            mailUtil.sendInviteMessage(m.getEmail(), EMAIL_AUTHENTICATION, teamId, m.getUserId());
        }
    }

    @Override
    public TeamMemberInviteRes confirmEmail(String email, String code) {
        InviteCode inviteCode = inviteRepository.findById(code);

        mailUtil.checkInviteCode(inviteCode.getCode(), code);
        Team team = teamRepository.findByTeamId(inviteCode.getTeamId());
        TeamValidator.validate(team);

        User user = userRepository.findByUserId(inviteCode.getUserId());
        UserValidator.validate(user);

        teamRoleRepository.save(TeamRole.builder().team(team).user(user).role(Role.MEMBER).build());
        inviteCodeService.deleteById(code); // 이미 등록된 사람 거르기

        return new TeamMemberInviteRes();
    }

    @Transactional
    @Override
    public TeamGetRes getTeamInfo(Long teamId, String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);

        Team team = teamRepository.findByTeamId(teamId);
        TeamValidator.validate(team);

        List<TeamRole> teamRoleList = team.getTeamRoleList();
        TeamRoleValidator.checkIsTeamMember(teamRoleList, user);

        return TeamServiceMapper.INSTANCE.toTeamGetRes(team);
    }

    @Transactional
    @Override
    public TeamMemberDeleteRes deleteMember(TeamMemberDeleteReq req) {

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user);

        User member = userRepository.findByUsername(req.getMemberName());
        UserValidator.validate(member);

        TeamRole myTeamRole = getTeamRole(req.getUsername(), req.getTeamId());
        TeamRole teamRole = getTeamRole(req.getMemberName(), req.getTeamId());
        TeamRoleValidator.validate(myTeamRole, req.getMemberName());

        teamRoleRepository.delete(teamRole);
        return new TeamMemberDeleteRes();
    }

    private TeamRole getTeamRole(String username, Long teamId) {
        TeamRole teamRole = teamRoleRepository.findByUserUsernameAndTeamTeamId(username, teamId);
        TeamRoleValidator.validate(teamRole);
        return teamRole;
    }
}
