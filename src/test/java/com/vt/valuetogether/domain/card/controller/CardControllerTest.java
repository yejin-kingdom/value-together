package com.vt.valuetogether.domain.card.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.service.CardService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
        String name = "name";
        String description = "desc";
        LocalDateTime deadline = LocalDateTime.now();
        CardSaveReq cardSaveReq =
                CardSaveReq.builder().name(name).description(description).deadline(deadline).build();

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
}
