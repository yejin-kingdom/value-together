package com.vt.valuetogether.domain.card.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG;

import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.infra.s3.S3Util;
import com.vt.valuetogether.test.CardTest;
import java.time.LocalDateTime;
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
}
