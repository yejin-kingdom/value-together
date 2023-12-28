package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.task.entity.Task;

public interface TaskTest extends ChecklistTest {

    Long TEST_TASK_ID = 1L;

    String TEST_CONTENT = "content";
    String TEST_UPDATED_CONTENT = "updatedContent";

    Boolean TEST_IS_COMPLETED = false;
    Boolean TEST_UPDATED_IS_COMPLETED = true;

    Task TEST_TASK =
            Task.builder()
                    .taskId(TEST_TASK_ID)
                    .content(TEST_CONTENT)
                    .isCompleted(TEST_IS_COMPLETED)
                    .checklist(TEST_CHECKLIST)
                    .build();

    Task TEST_UPDATED_TASK =
            Task.builder()
                    .taskId(TEST_TASK_ID)
                    .content(TEST_UPDATED_CONTENT)
                    .isCompleted(TEST_UPDATED_IS_COMPLETED)
                    .checklist(TEST_CHECKLIST)
                    .build();
}
