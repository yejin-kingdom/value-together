package com.vt.valuetogether.domain.team.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberInviteReq;
import com.vt.valuetogether.domain.team.dto.response.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.response.TeamGetRes;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.TeamRoleTest;
import com.vt.valuetogether.test.TeamTest;
import com.vt.valuetogether.test.UserTest;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest implements TeamTest {

    @InjectMocks private TeamServiceImpl teamService;
    @Mock private TeamRepository teamRepository;
    @Mock private TeamRoleRepository teamRoleRepository;
    @Mock private UserRepository userRepository;

    private Team team;

    @BeforeEach
    void setUp() {
        team =
                Team.builder()
                        .teamId(TEST_TEAM_ID)
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .isDeleted(TEST_TEAM_IS_DELETED)
                        .teamRoleList(List.of(TeamRoleTest.TEST_TEAM_ROLE))
                        .build();
    }

    @Test
    @DisplayName("team 조회 테스트")
    void team_조회() {
        // given
        given(userRepository.findByUsername(anyString())).willReturn(UserTest.TEST_USER);
        given(teamRepository.findByTeamId(anyLong())).willReturn(team);

        // when
        TeamGetRes actual = teamService.getTeamInfo(team.getTeamId(), UserTest.TEST_USER_NAME);

        // then
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(teamRepository, times(1)).findByTeamId(anyLong());
        Assertions.assertThat(actual.getMemberGetResList()).hasSize(1);
        Assertions.assertThat(actual)
                .extracting("teamName", "teamDescription", "backgroundColor")
                .contains(actual.getTeamName(), actual.getTeamDescription(), actual.getBackgroundColor());
    }

    @Test
    @DisplayName("team 생성 테스트")
    void team_생성() {
        // given
        TeamCreateReq req =
                TeamCreateReq.builder()
                        .teamName(TEST_TEAM_NAME)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .build();

        req.setUsername("username");

        given(teamRepository.findByTeamName(anyString())).willReturn(null);
        given(userRepository.findByUsername(anyString())).willReturn(UserTest.TEST_USER);
        given(teamRepository.save(any())).willReturn(TEST_TEAM);

        // when
        TeamCreateRes actual = teamService.createTeam(req);

        // then
        verify(teamRepository, times(1)).findByTeamName(anyString());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(teamRepository, times(1)).save(any(Team.class));
        verify(teamRoleRepository, times(1)).save(any(TeamRole.class));
        Assertions.assertThat(actual).extracting("teamId").isEqualTo(TEST_TEAM.getTeamId());
    }

    @Test
    @DisplayName("team 초대 테스트")
    void team_초대() {
        // given
        TeamMemberInviteReq req =
                TeamMemberInviteReq.builder()
                        .teamId(team.getTeamId())
                        .memberNameList(List.of(UserTest.TEST_USER_NAME))
                        .build();

        req.setUsername("username");

        given(teamRepository.findByTeamId(anyLong())).willReturn(team);
        given(teamRoleRepository.findByTeam_TeamId(anyLong()))
                .willReturn(List.of(TeamRoleTest.TEST_TEAM_ROLE));
        given(userRepository.findByUsername(anyString())).willReturn(UserTest.TEST_USER);
        given(userRepository.findAllByUsernameIn(anyList())).willReturn(List.of(UserTest.TEST_USER));

        // when
        teamService.inviteMember(req);

        // then
        verify(teamRepository, times(1)).findByTeamId(anyLong());
        verify(teamRoleRepository, times(1)).findByTeam_TeamId(anyLong());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findAllByUsernameIn(anyList());
    }

    @Test
    @DisplayName("team 삭제 테스트")
    void team_삭제() {
        // given
        TeamDeleteReq req = TeamDeleteReq.builder().teamId(team.getTeamId()).build();

        req.setUsername(UserTest.TEST_USER_NAME);
        given(userRepository.findByUsername(anyString())).willReturn(UserTest.TEST_USER);
        given(teamRepository.findByTeamId(anyLong())).willReturn(team);
        given(teamRoleRepository.findByTeam_TeamId(anyLong()))
                .willReturn(List.of(TeamRoleTest.TEST_TEAM_ROLE));

        // when
        teamService.deleteTeam(req);

        // then
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(teamRepository, times(1)).findByTeamId(anyLong());
        verify(teamRoleRepository, times(1)).findByTeam_TeamId(anyLong());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    @DisplayName("team member 삭제 테스트")
    void team_member_삭제() {
        // given
        TeamMemberDeleteReq req =
                TeamMemberDeleteReq.builder()
                        .teamId(team.getTeamId())
                        .memberName(UserTest.TEST_USER_NAME)
                        .build();

        req.setUsername(UserTest.TEST_USER_NAME);

        given(userRepository.findByUsername(anyString())).willReturn(UserTest.TEST_USER);
        given(teamRoleRepository.findByUserUsernameAndTeamTeamId(anyString(), anyLong()))
                .willReturn(TeamRoleTest.TEST_TEAM_ROLE);

        // when
        teamService.deleteMember(req);

        // then
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(teamRoleRepository, times(2)).findByUserUsernameAndTeamTeamId(anyString(), anyLong());
        verify(teamRoleRepository, times(1)).delete(any(TeamRole.class));
    }
}
