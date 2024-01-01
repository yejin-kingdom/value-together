package com.vt.valuetogether.domain.team.repository;

import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.test.TeamTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamRepositoryTest implements TeamTest {

    @Autowired private TeamRepository teamRepository;

    @Test
    @DisplayName("team 생성 테스트")
    void team_생성() {
        // given

        // when
        Team actual = teamRepository.save(TEST_TEAM);

        // then
        Assertions.assertThat(actual.getTeamName()).isEqualTo(TEST_TEAM_NAME);
        Assertions.assertThat(actual.getTeamDescription()).isEqualTo(TEST_TEAM_DESCRIPTION);
        Assertions.assertThat(actual.getBackgroundColor()).isEqualTo(TEST_BACKGROUND_COLOR);
    }

    @Test
    @DisplayName("findByTeamId 조회 테스트")
    void team_teamId_조회() {
        // given
        Team team = teamRepository.save(TEST_TEAM);

        // when
        Team actual = teamRepository.findByTeamId(team.getTeamId());

        // then
        Assertions.assertThat(actual)
                .extracting("teamId", "teamName", "teamDescription", "backgroundColor", "isDeleted")
                .contains(
                        team.getTeamId(),
                        team.getTeamName(),
                        team.getTeamDescription(),
                        team.getBackgroundColor(),
                        team.isDeleted());
    }

    @Test
    @DisplayName("findByTeamId 조회 테스트 - 실패")
    void team_teamId_조회_실패() {
        // given
        teamRepository.save(TEST_TEAM);
        Long failTeamId = 0L;

        // when
        Team actual = teamRepository.findByTeamId(failTeamId);

        // then
        Assertions.assertThat(actual).isNull();
    }

    @Test
    @DisplayName("findByTeamName 조회 테스트")
    void team_name_조회() {
        // given
        Team team = teamRepository.save(TEST_TEAM);

        // when
        Team actual = teamRepository.findByTeamName(TEST_TEAM_NAME);

        // then
        Assertions.assertThat(actual)
                .extracting("teamId", "teamName", "teamDescription", "backgroundColor", "isDeleted")
                .contains(
                        team.getTeamId(),
                        team.getTeamName(),
                        team.getTeamDescription(),
                        team.getBackgroundColor(),
                        team.isDeleted());
    }

    @Test
    @DisplayName("findByTeamName 조회 테스트 - 실패")
    void team_name_조회_실페() {
        // given
        teamRepository.save(TEST_TEAM);
        String failTeamName = "fail";

        // when
        Team actual = teamRepository.findByTeamName(failTeamName);

        // then
        Assertions.assertThat(actual).isNull();
    }

    @Test
    @DisplayName("team 삭제 테스트")
    void team_삭제() {
        // given
        Team team = teamRepository.save(TEST_TEAM);

        // when
        teamRepository.deleteByTeamId(team.getTeamId());

        Team actual = teamRepository.findByTeamId(team.getTeamId());

        // then
        Assertions.assertThat(actual).isNull();
    }
}
