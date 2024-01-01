package com.vt.valuetogether.domain.worker.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.test.WorkerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkerRepositoryTest implements WorkerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private WorkerRepository workerRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamRoleRepository teamRoleRepository;

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() {
        // given
        userRepository.save(TEST_USER);
        teamRepository.save(TEST_TEAM);
        TeamRole savedTeamRole = teamRoleRepository.save(TEST_TEAM_ROLE);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);

        // when
        Worker worker =
                workerRepository.save(Worker.builder().teamRole(savedTeamRole).card(TEST_CARD).build());

        // then
        assertThat(worker.getTeamRole().getTeamRoleId()).isEqualTo(savedTeamRole.getTeamRoleId());
    }

    @Test
    @DisplayName("id로 worker 조회 테스트")
    void id_worker_조회() {
        // given
        userRepository.save(TEST_USER);
        teamRepository.save(TEST_TEAM);
        TeamRole savedTeamRole = teamRoleRepository.save(TEST_TEAM_ROLE);
        categoryRepository.save(TEST_CATEGORY);
        Card savedCard = cardRepository.save(TEST_CARD);
        Worker savedWorker = workerRepository.save(TEST_WORKER);

        // when
        Worker worker = workerRepository.findByTeamRoleAndCard(savedTeamRole, savedCard);

        // then
        assertThat(worker.getTeamRole().getTeamRoleId())
                .isEqualTo(savedWorker.getTeamRole().getTeamRoleId());
    }

    @Test
    @DisplayName("worker 삭제 테스트")
    void worker_삭제() {
        // given
        userRepository.save(TEST_USER);
        teamRepository.save(TEST_TEAM);
        TeamRole teamRole = teamRoleRepository.save(TEST_TEAM_ROLE);
        categoryRepository.save(TEST_CATEGORY);
        Card card = cardRepository.save(Card.builder().build());
        Worker worker = workerRepository.save(Worker.builder().teamRole(teamRole).card(card).build());

        // when
        workerRepository.delete(worker);
        Worker findWorker = workerRepository.findByTeamRoleAndCard(teamRole, card);

        // then
        assertThat(findWorker).isNull();
    }
}
