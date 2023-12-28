package com.vt.valuetogether.domain.task.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.test.TaskTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest implements TaskTest {
    @Autowired private ChecklistRepository checklistRepository;
    @Autowired private TaskRepository taskRepository;

    @Test
    @DisplayName("task 저장 테스트")
    void task_저장() {
        // given
        checklistRepository.save(TEST_CHECKLIST);

        // when
        Task task = taskRepository.save(TEST_TASK);

        // then
        assertThat(task.getContent()).isEqualTo(TEST_CONTENT);
        assertThat(task.getIsCompleted()).isEqualTo(TEST_IS_COMPLETED);
    }
}
