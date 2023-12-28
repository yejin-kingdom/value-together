package com.vt.valuetogether.domain.task.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.repository.TaskRepository;
import com.vt.valuetogether.test.TaskTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest implements TaskTest {
    @InjectMocks private TaskServiceImpl taskService;

    @Mock private TaskRepository taskRepository;
    @Mock private ChecklistRepository checklistRepository;

    @Test
    @DisplayName("task 저장 테스트")
    void task_저장() {
        // given
        Long checklistId = 1L;
        String content = "content";
        TaskSaveReq taskSaveReq =
                TaskSaveReq.builder().checklistId(checklistId).content(content).build();
        when(checklistRepository.findByChecklistId(any())).thenReturn(TEST_CHECKLIST);
        when(taskRepository.save(any())).thenReturn(TEST_TASK);

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
}
