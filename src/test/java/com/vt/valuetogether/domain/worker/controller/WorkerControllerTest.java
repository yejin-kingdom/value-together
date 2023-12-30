package com.vt.valuetogether.domain.worker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.dto.response.WorkerAddRes;
import com.vt.valuetogether.domain.worker.dto.response.WorkerDeleteRes;
import com.vt.valuetogether.domain.worker.service.WorkerService;
import com.vt.valuetogether.test.WorkerTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {WorkerController.class})
class WorkerControllerTest extends BaseMvcTest implements WorkerTest {

    @MockBean private WorkerService workerService;

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() throws Exception {
        // given
        WorkerAddReq workerAddReq =
                WorkerAddReq.builder()
                        .cardId(TEST_CARD_ID)
                        .userIds(List.of(TEST_USER_ID, TEST_ANOTHER_USER_ID))
                        .build();
        WorkerAddRes workerAddRes = new WorkerAddRes();

        // when - then
        when(workerService.addWorker(any(WorkerAddReq.class))).thenReturn(workerAddRes);
        this.mockMvc
                .perform(
                        post("/api/v1/workers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(workerAddReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("worker 삭제 테스트")
    void worker_삭제() throws Exception {
        // given
        WorkerDeleteReq workerDeleteReq = WorkerDeleteReq.builder().cardId(TEST_CARD_ID).build();
        WorkerDeleteRes workerDeleteRes = new WorkerDeleteRes();

        // when - then
        when(workerService.deleteWorker(workerDeleteReq)).thenReturn(workerDeleteRes);
        this.mockMvc
                .perform(
                        delete("/api/v1/workers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(workerDeleteReq))
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
