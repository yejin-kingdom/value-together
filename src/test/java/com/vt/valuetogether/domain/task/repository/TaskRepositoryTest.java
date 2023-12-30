package com.vt.valuetogether.domain.task.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
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
    @Autowired private CardRepository cardRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;

    @Test
    @DisplayName("task 저장 테스트")
    void task_저장() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);
        checklistRepository.save(TEST_CHECKLIST);

        // when
        Task task = taskRepository.save(TEST_TASK);

        // then
        assertThat(task.getContent()).isEqualTo(TEST_CONTENT);
        assertThat(task.getIsCompleted()).isEqualTo(TEST_IS_COMPLETED);
    }

    @Test
    @DisplayName("id로 task 조회 테스트")
    void id_task_조회() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);
        checklistRepository.save(TEST_CHECKLIST);
        Task saveTask = taskRepository.save(TEST_TASK);

        // when
        Task task = taskRepository.findByTaskId(saveTask.getTaskId());

        // then
        assertThat(task.getContent()).isEqualTo(saveTask.getContent());
        assertThat(task.getIsCompleted()).isEqualTo(saveTask.getIsCompleted());
    }

    @Test
    @DisplayName("task 삭제 테스트")
    void task_삭제() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);
        checklistRepository.save(TEST_CHECKLIST);
        Task saveTask = taskRepository.save(TEST_TASK);

        // when
        taskRepository.delete(TEST_TASK);
        Task task = taskRepository.findByTaskId(saveTask.getTaskId());

        // then
        assertThat(task).isEqualTo(null);
    }
}
