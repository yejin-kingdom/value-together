package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.worker.entity.Worker;

public interface WorkerTest extends UserTest, CardTest {

    Worker TEST_WORKER = Worker.builder().user(TEST_USER).card(TEST_CARD).build();
}
