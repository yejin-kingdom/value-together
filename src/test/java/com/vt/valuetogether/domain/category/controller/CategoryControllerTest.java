package com.vt.valuetogether.domain.category.controller;

import static org.apache.commons.lang3.BooleanUtils.FALSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.card.dto.response.CardInnerCategoryRes;
import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryRestoreReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryDeleteRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryEditRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
import com.vt.valuetogether.domain.category.dto.response.CategoryRestoreRes;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.service.CategoryService;
import com.vt.valuetogether.domain.worker.dto.response.WorkerGetRes;
import com.vt.valuetogether.test.CategoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {CategoryController.class})
class CategoryControllerTest extends BaseMvcTest implements CategoryTest {

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

    @Test
    @DisplayName("teamId로 category 전체 조회 테스트")
    void teamId_category_전체_조회() throws Exception {
        Long teamId = 1L;
        String username = "ysys";
        String profileImageUrl = "profileUrl";
        WorkerGetRes workerGetRes =
                WorkerGetRes.builder().username(username).profileImageUrl(profileImageUrl).build();
        Long cardId = 1L;
        String cardName = "cardName";
        String description = "description";
        String fileUrl = "fileUrl";
        Double cardSequence = 1.0;
        String deadline = "2023.12.31 23:59:59";
        CardInnerCategoryRes cardInnerCategoryRes =
                CardInnerCategoryRes.builder()
                        .cardId(cardId)
                        .name(cardName)
                        .description(description)
                        .fileUrl(fileUrl)
                        .sequence(cardSequence)
                        .deadline(deadline)
                        .workers(List.of(workerGetRes))
                        .build();
        Long categoryId = 1L;
        String categoryName = "categoryName";
        Double categorySequence = 1.0;
        CategoryGetRes categoryGetRes =
                CategoryGetRes.builder()
                        .categoryId(categoryId)
                        .name(categoryName)
                        .sequence(categorySequence)
                        .cards(List.of(cardInnerCategoryRes))
                        .build();
        int total = 1;
        CategoryGetResList categoryGetResList =
                CategoryGetResList.builder().categories(List.of(categoryGetRes)).total(total).build();
        when(categoryService.getAllCategories(anyLong(), anyBoolean(), any()))
                .thenReturn(categoryGetResList);
        this.mockMvc
                .perform(
                        get("/api/v1/teams/{teamId}/categories", teamId)
                                .param("isDeleted", FALSE)
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("category 복구 테스트")
    void category_복구() throws Exception {
        Long categoryId = 1L;
        CategoryRestoreReq req = CategoryRestoreReq.builder().categoryId(categoryId).build();
        CategoryRestoreRes res = new CategoryRestoreRes();
        when(categoryService.restoreCategory(any())).thenReturn(res);
        this.mockMvc
                .perform(
                        patch("/api/v1/categories/restore")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 이름 수정")
    void category_이름_수정() throws Exception {
        CategoryEditReq req =
                CategoryEditReq.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_ANOTHER_CATEGORY_NAME)
                        .build();
        req.setName(this.mockPrincipal.getName());

        CategoryEditRes res = new CategoryEditRes();

        given(categoryService.editCategory(any(CategoryEditReq.class))).willReturn(res);

        this.mockMvc
                .perform(
                        patch("/api/v1/categories")
                                .principal(this.mockPrincipal)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void category_삭제() throws Exception {

        CategoryDeleteReq req = CategoryDeleteReq.builder().categoryId(TEST_CATEGORY_ID).build();
        req.setUsername(this.mockPrincipal.getName());

        CategoryDeleteRes res = new CategoryDeleteRes();

        given(categoryService.deleteCategory(req)).willReturn(res);

        this.mockMvc
                .perform(
                        delete("/api/v1/categories")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(APPLICATION_JSON)
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
