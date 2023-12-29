package com.vt.valuetogether.domain.worker.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.test.WorkerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class WorkerRepositoryTest implements WorkerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private WorkerRepository workerRepository;

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() {
        // given
        User savedUser = userRepository.save(TEST_USER);
        Card savedCard = cardRepository.save(TEST_CARD);

        // when
        Worker worker = workerRepository.save(TEST_WORKER);

        // then
        assertThat(worker.getUser().getUserId()).isEqualTo(savedUser.getUserId());
    }

    @Test
    @DisplayName("id로 worker 조회 테스트")
    void id_worker_조회() {
        // given
        userRepository.save(TEST_USER);
        cardRepository.save(TEST_CARD);
        Worker savedWorker = workerRepository.save(TEST_WORKER);

        // when
        Worker worker = workerRepository.findByWorkerId(savedWorker.getWorkerId());

        // then
        assertThat(worker.getWorkerId()).isEqualTo(savedWorker.getWorkerId());
    }

    @Test
    @DisplayName("worker 삭제 테스트")
    void worker_삭제() {
        // given
        userRepository.save(TEST_USER);
        cardRepository.save(TEST_CARD);
        Worker savedWorker = workerRepository.save(TEST_WORKER);

        // when
        workerRepository.delete(TEST_WORKER);
        Worker worker = workerRepository.findByWorkerId(TEST_WORKER_ID);

        // then
        assertThat(worker).isNull();
    }
}
