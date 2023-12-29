package com.vt.valuetogether.domain.card.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardDeleteRes;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistGetRes;
import com.vt.valuetogether.domain.task.dto.response.TaskGetRes;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(controllers = {CardController.class})
class CardControllerTest extends BaseMvcTest {
    @MockBean private CardService cardService;

    @Test
    @DisplayName("card 저장 테스트")
    void card_저장() throws Exception {
        Long cardId = 1L;
        Long categoryId = 1L;
        String name = "name";
        String description = "desc";
        LocalDateTime deadline = LocalDateTime.now();
        CardSaveReq cardSaveReq =
                CardSaveReq.builder()
                        .categoryId(categoryId)
                        .name(name)
                        .description(description)
                        .deadline(deadline)
                        .build();

        String imageUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file =
                new MockMultipartFile(
                        "image1",
                        fileResource.getFilename(),
                        IMAGE_JPEG.getType(),
                        fileResource.getInputStream());
        MockMultipartFile multipartFile =
                new MockMultipartFile("multipartFile", "orig", "multipart/form-data", file.getBytes());
        String json = objectMapper.writeValueAsString(cardSaveReq);
        MockMultipartFile req =
                new MockMultipartFile(
                        "cardSaveReq", "json", "application/json", json.getBytes(StandardCharsets.UTF_8));

        CardSaveRes cardSaveRes = CardSaveRes.builder().cardId(cardId).build();
        when(cardService.saveCard(any(), any())).thenReturn(cardSaveRes);
        this.mockMvc
                .perform(multipart("/api/v1/cards").file(multipartFile).file(req))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("card 수정 테스트")
    void card_수정() throws Exception {
        Long cardId = 1L;
        String name = "name";
        String description = "desc";
        LocalDateTime deadline = LocalDateTime.now();
        CardUpdateReq cardUpdateReq =
                CardUpdateReq.builder()
                        .cardId(cardId)
                        .name(name)
                        .description(description)
                        .deadline(deadline)
                        .build();

        String imageUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file =
                new MockMultipartFile(
                        "image1",
                        fileResource.getFilename(),
                        IMAGE_JPEG.getType(),
                        fileResource.getInputStream());
        MockMultipartFile multipartFile =
                new MockMultipartFile("multipartFile", "orig", "multipart/form-data", file.getBytes());
        String json = objectMapper.writeValueAsString(cardUpdateReq);
        MockMultipartFile req =
                new MockMultipartFile(
                        "cardUpdateReq", "json", "application/json", json.getBytes(StandardCharsets.UTF_8));

        CardUpdateRes cardUpdateRes = new CardUpdateRes();
        when(cardService.updateCard(any(), any())).thenReturn(cardUpdateRes);
        this.mockMvc
                .perform(multipart(PATCH, "/api/v1/cards").file(multipartFile).file(req))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("card 삭제 테스트")
    void card_삭제() throws Exception {
        Long cardId = 1L;
        CardDeleteReq cardDeleteReq = CardDeleteReq.builder().cardId(cardId).build();
        CardDeleteRes cardDeleteRes = new CardDeleteRes();
        when(cardService.deleteCard(any())).thenReturn(cardDeleteRes);
        this.mockMvc
                .perform(
                        delete("/api/v1/cards")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cardDeleteReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("card 단건 조회 테스트")
    void card_단건_조회() throws Exception {
        Long cardId = 1L;
        Long taskId = 1L;
        String content = "taskContent";
        Boolean isCompleted = false;
        TaskGetRes taskGetRes =
                TaskGetRes.builder().taskId(taskId).content(content).isCompleted(isCompleted).build();
        Long checklistId = 1L;
        String title = "checklistTitle";
        List<TaskGetRes> tasks = List.of(taskGetRes);
        ChecklistGetRes checklistGetRes =
                ChecklistGetRes.builder().checklistId(checklistId).title(title).tasks(tasks).build();
        String name = "cardName";
        String description = "cardDescription";
        String fileUrl = "fileUrl";
        String deadline = "2023.12.29 10:29:18";
        List<ChecklistGetRes> checklists = List.of(checklistGetRes);
        CardGetRes cardGetRes =
                CardGetRes.builder()
                        .cardId(cardId)
                        .name(name)
                        .description(description)
                        .fileUrl(fileUrl)
                        .deadline(deadline)
                        .checklists(checklists)
                        .build();
        when(cardService.getCard(any())).thenReturn(cardGetRes);
        this.mockMvc.perform(get("/api/v1/cards/" + cardId)).andDo(print()).andExpect(status().isOk());
    }
}
