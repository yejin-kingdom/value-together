package com.vt.valuetogether.domain.task.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.dto.request.TaskDeleteReq;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.domain.task.repository.TaskRepository;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.TaskTest;
import com.vt.valuetogether.test.TeamRoleTest;
import com.vt.valuetogether.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest implements TaskTest, UserTest, TeamRoleTest {
    @InjectMocks private TaskServiceImpl taskService;

    @Mock private TaskRepository taskRepository;
    @Mock private ChecklistRepository checklistRepository;
    @Mock private UserRepository userRepository;
    private Category category;
    private Team team;
    private Card card;
    private Checklist checklist;
    private Task task;

    @BeforeEach
    void setUp() {
        team =
                Team.builder()
                        .teamId(TEST_TEAM_ID)
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .isDeleted(TEST_TEAM_IS_DELETED)
                        .teamRoleList(List.of(TEST_TEAM_ROLE))
                        .build();
        category =
                Category.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_CATEGORY_NAME)
                        .sequence(TEST_CATEGORY_SEQUENCE)
                        .isDeleted(TEST_CATEGORY_IS_DELETED)
                        .team(team)
                        .build();
        card =
                Card.builder()
                        .cardId(TEST_CARD_ID)
                        .name(TEST_NAME)
                        .description(TEST_DESCRIPTION)
                        .fileUrl(TEST_FILE_URL)
                        .sequence(TEST_SEQUENCE)
                        .deadline(TEST_DEADLINE)
                        .category(category)
                        .build();
        checklist =
                Checklist.builder().checklistId(TEST_CHECKLIST_ID).title(TEST_TITLE).card(card).build();
        task =
                Task.builder()
                        .taskId(TEST_TASK_ID)
                        .content(TEST_CONTENT)
                        .isCompleted(TEST_IS_COMPLETED)
                        .checklist(checklist)
                        .build();
    }

    @Test
    @DisplayName("task 저장 테스트")
    void task_저장() {
        // given
        Long checklistId = 1L;
        String content = "content";
        TaskSaveReq taskSaveReq =
                TaskSaveReq.builder().checklistId(checklistId).content(content).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(checklistRepository.findByChecklistId(any())).thenReturn(checklist);
        when(taskRepository.save(any())).thenReturn(task);

        // when
        taskService.saveTask(taskSaveReq);

        // then
        verify(checklistRepository).findByChecklistId(any());
        verify(taskRepository).save(any());
    }

    @Test
    @DisplayName("task 수정 테스트")
    void task_수정() {
        // given
        Long taskId = 1L;
        String content = "updatedContent";
        Boolean isCompleted = true;
        TaskUpdateReq taskUpdateReq =
                TaskUpdateReq.builder().taskId(taskId).content(content).isCompleted(isCompleted).build();
        when(taskRepository.findByTaskId(any())).thenReturn(TEST_TASK);
        when(taskRepository.save(any())).thenReturn(TEST_UPDATED_TASK);

        // when
        taskService.updateTask(taskUpdateReq);

        // then
        verify(taskRepository).findByTaskId(any());
        verify(taskRepository).save(any());
    }

    @Test
    @DisplayName("task 삭제 테스트")
    void task_삭제() {
        // given
        Long taskId = 1L;
        TaskDeleteReq taskDeleteReq = TaskDeleteReq.builder().taskId(taskId).build();
        when(taskRepository.findByTaskId(any())).thenReturn(TEST_TASK);

        // when
        taskService.deleteTask(taskDeleteReq);

        // then
        verify(taskRepository).findByTaskId(any());
        verify(taskRepository).delete(any());
    }
}
