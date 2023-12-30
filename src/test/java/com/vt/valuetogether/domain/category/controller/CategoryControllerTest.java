package com.vt.valuetogether.domain.category.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {CategoryController.class})
class CategoryControllerTest extends BaseMvcTest {
    @MockBean private CategoryService categoryService;

    @Test
    @DisplayName("category 저장 테스트")
    void category_저장() throws Exception {
        Long teamId = 1L;
        String name = "category";
        CategorySaveReq categorySaveReq = CategorySaveReq.builder().teamId(teamId).name(name).build();
        Long categoryId = 1L;
        CategorySaveRes categorySaveRes = CategorySaveRes.builder().categoryId(categoryId).build();
        when(categoryService.saveCategory(any())).thenReturn(categorySaveRes);
        this.mockMvc
                .perform(
                        post("/api/v1/categories")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(categorySaveReq))
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("category 순서 변경 테스트")
    void category_순서_변경() throws Exception {
        Long categoryId = 1L;
        Double preSequence = 2.0;
        Double postSequence = 3.0;
        CategoryChangeSequenceReq categoryChangeSequenceReq =
                CategoryChangeSequenceReq.builder()
                        .categoryId(categoryId)
                        .preSequence(preSequence)
                        .postSequence(postSequence)
                        .build();
        CategoryChangeSequenceRes categoryChangeSequenceRes = new CategoryChangeSequenceRes();
        when(categoryService.changeCategorySequence(any())).thenReturn(categoryChangeSequenceRes);
        this.mockMvc
                .perform(
                        patch("/api/v1/categories/order")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(categoryChangeSequenceReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
