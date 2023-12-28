package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.task.entity.Task;

public interface TaskTest extends ChecklistTest {

    Long TEST_TASK_ID = 1L;

    String TEST_CONTENT = "content";

    Boolean TEST_IS_COMPLETED = false;

    Task TEST_TASK =
            Task.builder()
                    .taskId(TEST_TASK_ID)
                    .content(TEST_CONTENT)
                    .isCompleted(TEST_IS_COMPLETED)
                    .checklist(TEST_CHECKLIST)
                    .build();
}
