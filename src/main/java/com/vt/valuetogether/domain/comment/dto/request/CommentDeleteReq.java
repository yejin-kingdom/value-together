package com.vt.valuetogether.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteReq {
    private Long commentId;
    private String username;

    @Builder
    private CommentDeleteReq(Long commentId, String username) {
        this.commentId = commentId;
        this.username = username;
    }
}
