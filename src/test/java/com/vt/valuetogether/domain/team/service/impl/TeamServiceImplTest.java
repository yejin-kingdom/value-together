package com.vt.valuetogether.domain.team.service.impl;

import static com.vt.valuetogether.test.TeamTest.TEST_TEAM;
import static com.vt.valuetogether.test.UserTest.TEST_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @InjectMocks private TeamServiceImpl teamService;

    @Mock private TeamRepository teamRepository;

    @Mock private TeamRoleRepository teamRoleRepository;

    @Mock private UserRepository userRepository;

    @Test
    @DisplayName("team 생성 테스트")
    void team_생성() {
        // given
        TeamCreateReq req =
                TeamCreateReq.builder().teamName("testName").backgroundColor("#12345").build();

        req.setUsername("username");

        given(teamRepository.findByTeamName(anyString())).willReturn(null);
        given(userRepository.findByUsername(anyString())).willReturn(TEST_USER);
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
}
