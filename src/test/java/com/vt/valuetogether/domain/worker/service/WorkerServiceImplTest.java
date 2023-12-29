package com.vt.valuetogether.domain.worker.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.domain.worker.repository.WorkerRepository;
import com.vt.valuetogether.domain.worker.service.impl.WorkerServiceImpl;
import com.vt.valuetogether.test.WorkerTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest implements WorkerTest {

    @Mock
    UserRepository userRepository;
    @Mock
    CardRepository cardRepository;
    @Mock
    WorkerRepository workerRepository;
    @InjectMocks
    private WorkerServiceImpl workerService;

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() {
        // given
        WorkerAddReq workerAddReq = WorkerAddReq.builder().cardId(TEST_CARD_ID)
            .userIds(List.of(TEST_USER_ID, TEST_ANOTHER_USER_ID)).build();

        given(cardRepository.findByCardId(anyLong())).willReturn(TEST_CARD);
        given(userRepository.findByUsername(any())).willReturn(TEST_USER);
        given(workerRepository.save(any(Worker.class))).willReturn(TEST_WORKER);

        // when
        workerService.addWorker(workerAddReq);

        // then
        verify(cardRepository).findByCardId(any());
        verify(userRepository).findByUsername(any());
        verify(workerRepository).save(any(Worker.class));
    }

    @Test
    @DisplayName("worker 삭제  테스트")
    void worker_삭제() {
        // given
        WorkerDeleteReq workerDeleteReq = WorkerDeleteReq.builder().workerId(TEST_WORKER_ID)
            .build();

        given(workerRepository.findByWorkerId(anyLong())).willReturn(TEST_WORKER);

        // when
        workerService.deleteWorker(workerDeleteReq);

        // then
        verify(workerRepository).delete(any(Worker.class));
    }
}