package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.comment.entity.Comment;

public interface CommentTest extends TeamRoleTest, CardTest {
    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "test content";

    String TEST_COMMENT_ANOTHER_CONTENT = "updated content";

    Comment TEST_COMMENT =
            Comment.builder()
                    .commentId(TEST_COMMENT_ID)
                    .content(TEST_COMMENT_CONTENT)
                    .card(TEST_CARD)
                    .teamRole(TEST_TEAM_ROLE)
                    .build();
}
