package com.vt.valuetogether.domain.card.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG;

import com.vt.valuetogether.domain.card.dto.request.CardChangeSequenceReq;
import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.infra.s3.S3Util;
import com.vt.valuetogether.test.CardTest;
import com.vt.valuetogether.test.TeamRoleTest;
import com.vt.valuetogether.test.UserTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
class CardServiceImplTest implements CardTest, UserTest, TeamRoleTest {
    @InjectMocks private CardServiceImpl cardService;

    @Mock private CardRepository cardRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private UserRepository userRepository;
    @Mock private S3Util s3Util;
    private Category category;
    private Team team;
    private Card card;

    @BeforeEach
    void setUp() {
        team =
                Team.builder()
                        .teamId(TEST_TEAM_ID)
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .isDeleted(TEST_TEAM_IS_DELETED)
                        .teamRoleList(List.of(TEST_TEAM_ROLE))
                        .build();
        category =
                Category.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_CATEGORY_NAME)
                        .sequence(TEST_CATEGORY_SEQUENCE)
                        .isDeleted(TEST_CATEGORY_IS_DELETED)
                        .team(team)
                        .build();
        card =
                Card.builder()
                        .cardId(TEST_CARD_ID)
                        .name(TEST_NAME)
                        .description(TEST_DESCRIPTION)
                        .fileUrl(TEST_FILE_URL)
                        .sequence(TEST_SEQUENCE)
                        .deadline(TEST_DEADLINE)
                        .category(category)
                        .build();
    }

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
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(categoryRepository.findByCategoryId(any())).thenReturn(category);
        when(cardRepository.getMaxSequence(any())).thenReturn(TEST_SEQUENCE);
        when(cardRepository.save(any())).thenReturn(card);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_FILE_URL);

        // when
        cardService.saveCard(cardSaveReq, multipartFile);

        // then
        verify(userRepository).findByUsername(any());
        verify(categoryRepository).findByCategoryId(any());
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
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(cardRepository.findByCardId(any())).thenReturn(card);
        when(cardRepository.save(any())).thenReturn(TEST_UPDATED_CARD);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_FILE_URL);

        // when
        cardService.updateCard(cardUpdateReq, multipartFile);

        // then
        verify(userRepository).findByUsername(any());
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
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(cardRepository.findByCardId(any())).thenReturn(card);

        // when
        cardService.deleteCard(cardDeleteReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(cardRepository).findByCardId(any());
        verify(cardRepository).delete(any());
    }

    @Test
    @DisplayName("card 단건 조회 테스트")
    void card_단건_조회() {
        // given
        Long cardId = 1L;
        String username = "ysys";
        ZonedDateTime zonedDateTime = TEST_DEADLINE.atZone(ZoneId.of("Asia/Seoul"));
        String deadline = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(cardRepository.findByCardId(any())).thenReturn(card);

        // when
        CardGetRes cardGetRes = cardService.getCard(cardId, username);

        // then
        assertThat(cardGetRes.getName()).isEqualTo(TEST_NAME);
        assertThat(cardGetRes.getDescription()).isEqualTo(TEST_DESCRIPTION);
        assertThat(cardGetRes.getFileUrl()).isEqualTo(TEST_FILE_URL);
        assertThat(cardGetRes.getDeadline()).isEqualTo(deadline);
        verify(userRepository).findByUsername(any());
        verify(cardRepository).findByCardId(any());
    }

    @Test
    @DisplayName("card 순서 이동 테스트")
    void card_순서_이동() {
        // given
        Long categoryId = 1L;
        Long cardId = 1L;
        Double preSequence = 2.0;
        Double postSequence = 3.0;

        CardChangeSequenceReq cardChangeSequenceReq =
                CardChangeSequenceReq.builder()
                        .categoryId(categoryId)
                        .cardId(cardId)
                        .preSequence(preSequence)
                        .postSequence(postSequence)
                        .build();

        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        given(cardRepository.findByCardId(any())).willReturn(card);
        when(categoryRepository.findByCategoryId(any())).thenReturn(category);
        when(cardRepository.save(any())).thenReturn(TEST_ANOTHER_CARD);

        // when
        cardService.changeSequence(cardChangeSequenceReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(cardRepository).findByCardId(any());
        verify(categoryRepository).findByCategoryId(any());
        verify(cardRepository, times(1)).save(any(Card.class));
    }
}
