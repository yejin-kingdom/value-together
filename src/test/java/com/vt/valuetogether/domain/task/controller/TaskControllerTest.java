package com.vt.valuetogether.domain.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.task.dto.request.TaskDeleteReq;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.dto.response.TaskDeleteRes;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.dto.response.TaskUpdateRes;
import com.vt.valuetogether.domain.task.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {TaskController.class})
class TaskControllerTest extends BaseMvcTest {
    @MockBean private TaskService taskService;

    @Test
    @DisplayName("task 저장 테스트")
    void task_저장() throws Exception {
        Long checklistId = 1L;
        String content = "content";
        Long taskId = 1L;
        TaskSaveReq taskSaveReq =
                TaskSaveReq.builder().checklistId(checklistId).content(content).build();
        TaskSaveRes taskSaveRes = TaskSaveRes.builder().taskId(taskId).build();
        when(taskService.saveTask(any())).thenReturn(taskSaveRes);
        this.mockMvc
                .perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(taskSaveReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("task 수정 테스트")
    void task_수정() throws Exception {
        Long taskId = 1L;
        String content = "updatedContent";
        Boolean isCompleted = true;
        TaskUpdateReq taskUpdateReq =
                TaskUpdateReq.builder().taskId(taskId).content(content).isCompleted(isCompleted).build();
        TaskUpdateRes taskUpdateRes = new TaskUpdateRes();
        when(taskService.updateTask(any())).thenReturn(taskUpdateRes);
        this.mockMvc
                .perform(
                        patch("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(taskUpdateReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("task 삭제 테스트")
    void task_삭제() throws Exception {
        Long taskId = 1L;
        TaskDeleteReq taskDeleteReq = TaskDeleteReq.builder().taskId(taskId).build();
        TaskDeleteRes taskDeleteRes = new TaskDeleteRes();
        when(taskService.deleteTask(any())).thenReturn(taskDeleteRes);
        this.mockMvc
                .perform(
                        delete("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(taskDeleteReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
