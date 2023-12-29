package com.vt.valuetogether.domain.card.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG;

import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.infra.s3.S3Util;
import com.vt.valuetogether.test.CardTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest implements CardTest {
    @InjectMocks private CardServiceImpl cardService;

    @Mock private CardRepository cardRepository;
    @Mock private S3Util s3Util;

    @Test
    @DisplayName("card 저장 테스트")
    void card_저장() throws Exception {
        // given
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

        String fileUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(fileUrl);
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "image1",
                        fileResource.getFilename(),
                        IMAGE_JPEG.getType(),
                        fileResource.getInputStream());
        when(cardRepository.getMaxSequence(any())).thenReturn(TEST_SEQUENCE);
        when(cardRepository.save(any())).thenReturn(TEST_CARD);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_FILE_URL);

        // when
        cardService.saveCard(cardSaveReq, multipartFile);

        // then
        verify(cardRepository).getMaxSequence(any());
        verify(cardRepository).save(any());
        verify(s3Util).uploadFile(any(), any());
    }

    @Test
    @DisplayName("card 수정 테스트")
    void card_수정() throws Exception {
        // given
        Long cardId = 1L;
        String name = "updatedName";
        String description = "updatedDesc";
        LocalDateTime deadline = LocalDateTime.now();
        CardUpdateReq cardUpdateReq =
                CardUpdateReq.builder()
                        .cardId(cardId)
                        .name(name)
                        .description(description)
                        .deadline(deadline)
                        .build();

        String fileUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(fileUrl);
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "image1",
                        fileResource.getFilename(),
                        IMAGE_JPEG.getType(),
                        fileResource.getInputStream());
        when(cardRepository.findByCardId(any())).thenReturn(TEST_CARD);
        when(cardRepository.save(any())).thenReturn(TEST_UPDATED_CARD);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_FILE_URL);

        // when
        cardService.updateCard(cardUpdateReq, multipartFile);

        // then
        verify(cardRepository).findByCardId(any());
        verify(cardRepository).save(any());
        verify(s3Util).uploadFile(any(), any());
    }

    @Test
    @DisplayName("card 삭제 테스트")
    void card_삭제() {
        // given
        Long cardId = 1L;
        CardDeleteReq cardDeleteReq = CardDeleteReq.builder().cardId(cardId).build();
        when(cardRepository.findByCardId(any())).thenReturn(TEST_CARD);

        // when
        cardService.deleteCard(cardDeleteReq);

        // then
        verify(cardRepository).findByCardId(any());
        verify(cardRepository).delete(any());
    }

    @Test
    @DisplayName("card 단건 조회 테스트")
    void card_단건_조회() {
        // given
        Long cardId = 1L;
        ZonedDateTime zonedDateTime = TEST_DEADLINE.atZone(ZoneId.of("Asia/Seoul"));
        String deadline = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        when(cardRepository.findByCardId(any())).thenReturn(TEST_CARD);

        // when
        CardGetRes cardGetRes = cardService.getCard(cardId);

        // then
        assertThat(cardGetRes.getName()).isEqualTo(TEST_NAME);
        assertThat(cardGetRes.getDescription()).isEqualTo(TEST_DESCRIPTION);
        assertThat(cardGetRes.getFileUrl()).isEqualTo(TEST_FILE_URL);
        assertThat(cardGetRes.getDeadline()).isEqualTo(deadline);
        verify(cardRepository).findByCardId(any());
    }
}
