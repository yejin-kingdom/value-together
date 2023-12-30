package com.vt.valuetogether.domain.category.controller;

import static java.lang.Boolean.FALSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.service.CategoryService;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistGetRes;
import com.vt.valuetogether.domain.task.dto.response.TaskGetRes;
import java.util.List;
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

    @Test
    @DisplayName("teamId로 category 전체 조회 테스트")
    void teamId_category_전체_조회() throws Exception {
        Long teamId = 1L;
        Long taskId = 1L;
        String content = "taskContent";
        Boolean isCompleted = FALSE;
        TaskGetRes taskGetRes =
                TaskGetRes.builder().taskId(taskId).content(content).isCompleted(isCompleted).build();
        Long checklistId = 1L;
        String title = "checklistTitle";
        ChecklistGetRes checklistGetRes =
                ChecklistGetRes.builder()
                        .checklistId(checklistId)
                        .title(title)
                        .tasks(List.of(taskGetRes))
                        .build();
        Long cardId = 1L;
        String cardName = "cardName";
        String description = "description";
        String fileUrl = "fileUrl";
        Double cardSequence = 1.0;
        String deadline = "2023.12.31 23:59:59";
        CardGetRes cardGetRes =
                CardGetRes.builder()
                        .cardId(cardId)
                        .name(cardName)
                        .description(description)
                        .fileUrl(fileUrl)
                        .sequence(cardSequence)
                        .deadline(deadline)
                        .checklists(List.of(checklistGetRes))
                        .build();
        Long categoryId = 1L;
        String categoryName = "categoryName";
        Double categorySequence = 1.0;
        Boolean isDeleted = FALSE;
        CategoryGetRes categoryGetRes =
                CategoryGetRes.builder()
                        .categoryId(categoryId)
                        .name(categoryName)
                        .sequence(categorySequence)
                        .isDeleted(isDeleted)
                        .cards(List.of(cardGetRes))
                        .build();
        int total = 1;
        CategoryGetResList categoryGetResList =
                CategoryGetResList.builder().categories(List.of(categoryGetRes)).total(total).build();
        when(categoryService.getAllCategories(any(), any())).thenReturn(categoryGetResList);
        this.mockMvc
                .perform(get("/api/v1/teams/" + teamId + "/categories").principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
