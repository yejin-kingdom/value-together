package com.vt.valuetogether.domain.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.sample.dto.request.SampleSaveReq;
import com.vt.valuetogether.domain.sample.dto.response.SampleGetRes;
import com.vt.valuetogether.domain.sample.dto.response.SampleGetResList;
import com.vt.valuetogether.domain.sample.dto.response.SampleSaveRes;
import com.vt.valuetogether.domain.sample.servce.SampleService;
import java.util.List;
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
        SampleSaveRes sampleSaveRes = SampleSaveRes.builder().sampleId(1L).build();
        when(sampleService.saveSample(any())).thenReturn(sampleSaveRes);
        this.mockMvc
                .perform(
                        post("/api/v1/sample")
                                .content(objectMapper.writeValueAsString(sampleSaveReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 샘플_전체_조회() throws Exception {
        SampleGetRes sampleGetRes =
                SampleGetRes.builder().sampleId(1L).title("title").text("text").build();
        SampleGetResList sampleGetResList =
                SampleGetResList.builder().sampleGetReses(List.of(sampleGetRes)).total(1).build();
        when(sampleService.getAllSamples()).thenReturn(sampleGetResList);
        this.mockMvc.perform(get("/api/v1/sample")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 샘플_단건_조회() throws Exception {
        Long sampleId = 1L;
        SampleGetRes sampleGetRes =
                SampleGetRes.builder().sampleId(1L).title("title").text("text").build();
        when(sampleService.getSample(any())).thenReturn(sampleGetRes);
        this.mockMvc
                .perform(get("/api/v1/sample/" + sampleId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
