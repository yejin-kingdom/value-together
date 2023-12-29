package com.vt.valuetogether.domain.comment.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.CardTest;
import com.vt.valuetogether.test.CommentTest;
import com.vt.valuetogether.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest implements CommentTest, CardTest, UserTest {

    @Autowired private CommentRepository commentRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private CardRepository cardRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        userRepository.save(TEST_USER);
        cardRepository.save(TEST_CARD);
        Comment comment =
                Comment.builder().content(TEST_COMMENT_CONTENT).card(TEST_CARD).user(TEST_USER).build();

        // when
        Comment saveComment = commentRepository.save(comment);

        // then
        assertEquals(saveComment.getContent(), comment.getContent());
        assertEquals(saveComment.getUser(), comment.getUser());
        assertEquals(saveComment.getCard(), comment.getCard());
    }
}
