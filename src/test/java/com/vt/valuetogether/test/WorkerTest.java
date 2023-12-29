package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.worker.entity.Worker;

public interface WorkerTest extends UserTest, CardTest {

    Long TEST_WORKER_ID = 1L;

    Worker TEST_WORKER = Worker.builder()
        .workerId(TEST_WORKER_ID)
        .user(TEST_USER)
        .card(TEST_CARD)
        .build();
}
