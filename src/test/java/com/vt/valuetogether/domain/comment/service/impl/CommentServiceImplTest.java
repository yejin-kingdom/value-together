package com.vt.valuetogether.domain.comment.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.comment.repository.CommentRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.CardTest;
import com.vt.valuetogether.test.CommentTest;
import com.vt.valuetogether.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest implements CommentTest, UserTest, CardTest {
    @Mock CommentRepository commentRepository;

    @Mock UserRepository userRepository;

    @Mock CardRepository cardRepository;

    @InjectMocks CommentServiceImpl commentService;

    @Captor ArgumentCaptor<Comment> argumentCaptor;

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
        given(cardRepository.findByCardId(req.getCardId())).willReturn(TEST_CARD);

        // when
        commentService.saveComment(req);

        // then
        verify(commentRepository).save(argumentCaptor.capture());
        assertEquals(TEST_COMMENT_CONTENT, argumentCaptor.getValue().getContent());
        assertEquals(TEST_DESCRIPTION, argumentCaptor.getValue().getCard().getDescription());
        assertEquals(TEST_USER_EMAIL, argumentCaptor.getValue().getUser().getEmail());
    }
}
