package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.worker.entity.Worker;

public interface WorkerTest extends TeamRoleTest, CardTest {

    Worker TEST_WORKER = Worker.builder().teamRole(TEST_TEAM_ROLE).card(TEST_CARD).build();
}
