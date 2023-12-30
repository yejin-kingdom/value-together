package com.vt.valuetogether.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentGetRes {
    private Long commentId;
    private String content;
    private String username;

    @Builder
    private CommentGetRes(Long commentId, String content, String username) {
        this.commentId = commentId;
        this.content = content;
        this.username = username;
    }
}
