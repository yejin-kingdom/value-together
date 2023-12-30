package com.vt.valuetogether.domain.comment.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.CommentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest implements CommentTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        userRepository.save(TEST_USER);
        cardRepository.save(TEST_CARD);

        // when
        Comment saveComment = commentRepository.save(TEST_COMMENT);

        // then
        assertEquals(saveComment.getContent(), TEST_COMMENT.getContent());
        assertEquals(saveComment.getUser().getUserId(), TEST_COMMENT.getUser().getUserId());
        assertEquals(saveComment.getCard().getCardId(), TEST_COMMENT.getCard().getCardId());
    }
}
