package com.vt.valuetogether.domain.comment.service.impl;

import static com.vt.valuetogether.global.meta.ResultCode.FORBIDDEN_TEAM_ROLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.comment.dto.request.CommentDeleteReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentUpdateReq;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.comment.repository.CommentRepository;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.test.CommentTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest implements CommentTest {
    @Mock CommentRepository commentRepository;

    @Mock UserRepository userRepository;

    @Mock CardRepository cardRepository;

    @InjectMocks CommentServiceImpl commentService;

    @Captor ArgumentCaptor<Comment> argumentCaptor;

    private Category category;
    private Team team;
    private Card card;
    private Comment comment;

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

        comment =
                Comment.builder()
                        .commentId(TEST_COMMENT_ID)
                        .content(TEST_COMMENT_CONTENT)
                        .user(TEST_USER)
                        .card(card)
                        .build();
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    void saveCommentTest() {
        // given
        CommentSaveReq req =
                CommentSaveReq.builder()
                        .cardId(TEST_CARD_ID)
                        .content(TEST_COMMENT_CONTENT)
                        .username(TEST_USER_NAME)
                        .build();

        given(userRepository.findByUsername(req.getUsername())).willReturn(TEST_USER);
        given(cardRepository.findByCardId(req.getCardId())).willReturn(card);

        // when
        commentService.saveComment(req);

        // then
        verify(commentRepository).save(argumentCaptor.capture());
        assertEquals(TEST_COMMENT_CONTENT, argumentCaptor.getValue().getContent());
        assertEquals(TEST_DESCRIPTION, argumentCaptor.getValue().getCard().getDescription());
        assertEquals(TEST_USER_EMAIL, argumentCaptor.getValue().getUser().getEmail());
    }

    @Test
    @DisplayName("댓글 저장 실패 테스트 - 사용자 권한 없음")
    void invalidSaveCommentTest() {
        // given
        CommentSaveReq req =
                CommentSaveReq.builder()
                        .cardId(TEST_CARD_ID)
                        .content(TEST_COMMENT_CONTENT)
                        .username(TEST_USER_NAME)
                        .build();

        given(userRepository.findByUsername(req.getUsername())).willReturn(TEST_ANOTHER_USER);
        given(cardRepository.findByCardId(req.getCardId())).willReturn(card);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            commentService.saveComment(req);
                        });

        // then
        assertEquals(FORBIDDEN_TEAM_ROLE.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // given
        CommentUpdateReq req =
                CommentUpdateReq.builder()
                        .commentId(TEST_COMMENT_ID)
                        .content(TEST_COMMENT_ANOTHER_CONTENT)
                        .username(TEST_USER_NAME)
                        .build();

        given(commentRepository.findByCommentId(req.getCommentId())).willReturn(comment);

        // when
        commentService.updateComment(req);

        // then
        verify(commentRepository).save(argumentCaptor.capture());
        assertEquals(TEST_COMMENT_ANOTHER_CONTENT, argumentCaptor.getValue().getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        // given
        CommentDeleteReq req =
                CommentDeleteReq.builder().commentId(TEST_COMMENT_ID).username(TEST_USER_NAME).build();

        given(commentRepository.findByCommentId(req.getCommentId())).willReturn(comment);

        // when
        commentService.deleteComment(req);

        // then
        verify(commentRepository).delete(any());
    }
}
