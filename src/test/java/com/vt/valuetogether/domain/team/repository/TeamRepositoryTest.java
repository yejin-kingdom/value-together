package com.vt.valuetogether.domain.team.repository;


import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.TeamTest;
import com.vt.valuetogether.test.UserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("team 생성 테스트")
    void team_생성() {
        // given
        userRepository.save(UserTest.TEST_USER);

        // when
        Team actual = teamRepository.save(TeamTest.TEST_TEAM);

        // then
        Assertions.assertThat(actual.getTeamId()).isEqualTo(TeamTest.TEST_TEAM_ID);
        Assertions.assertThat(actual.getTeamName()).isEqualTo(TeamTest.TEST_TEAM_NAME);
        Assertions.assertThat(actual.getTeamDescription()).isEqualTo(TeamTest.TEST_TEAM_DESCRIPTION);
        Assertions.assertThat(actual.getBackgroundColor()).isEqualTo(TeamTest.TEST_BACKGROUND_COLOR);
     }

}
