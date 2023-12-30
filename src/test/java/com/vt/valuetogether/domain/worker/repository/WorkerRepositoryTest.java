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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkerRepositoryTest implements WorkerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private WorkerRepository workerRepository;

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() {
        // given
        User savedUser = userRepository.save(User.builder().build());
        cardRepository.save(TEST_CARD);

        // when
        Worker worker = workerRepository.save(Worker.builder().user(savedUser).card(TEST_CARD).build());

        // then
        assertThat(worker.getUser().getUserId()).isEqualTo(savedUser.getUserId());
    }

    @Test
    @DisplayName("id로 worker 조회 테스트")
    void id_worker_조회() {
        // given
        User savedUser = userRepository.save(TEST_USER);
        Card savedCard = cardRepository.save(TEST_CARD);
        Worker savedWorker = workerRepository.save(TEST_WORKER);

        // when
        Worker worker = workerRepository.findByUserAndCard(savedUser, savedCard);

        // then
        assertThat(worker.getUser().getUserId()).isEqualTo(savedWorker.getUser().getUserId());
    }

    @Test
    @DisplayName("worker 삭제 테스트")
    void worker_삭제() {
        // given
        User user = userRepository.save(User.builder().build());
        Card card = cardRepository.save(Card.builder().build());
        Worker worker = workerRepository.save(Worker.builder().user(user).card(card).build());

        // when
        workerRepository.delete(worker);
        Worker findWorker = workerRepository.findByUserAndCard(user, card);

        // then
        assertThat(findWorker).isNull();
    }
}
