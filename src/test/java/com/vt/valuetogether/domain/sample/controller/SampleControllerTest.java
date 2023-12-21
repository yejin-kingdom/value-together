package com.vt.valuetogether.domain.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.sample.dto.request.SampleSaveReq;
import com.vt.valuetogether.domain.sample.dto.response.SampleSaveRes;
import com.vt.valuetogether.domain.sample.servce.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = {SampleController.class})
class SampleControllerTest extends BaseMvcTest {

    @MockBean private SampleService sampleService;

    @Test
    void 샘플_저장() throws Exception {
        SampleSaveReq sampleSaveReq = SampleSaveReq.builder().title("title").text("text").build();
        SampleSaveRes sampleSaveRes = new SampleSaveRes();
        when(sampleService.saveSample(any())).thenReturn(sampleSaveRes);
        this.mockMvc
                .perform(
                        post("/api/v1/sample")
                                .content(objectMapper.writeValueAsString(sampleSaveReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
