package com.vt.valuetogether.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateReq {
    private Long commentId;
    private String username;
    private String content;

    @Builder
    private CommentUpdateReq(Long commentId, String username, String content) {
        this.commentId = commentId;
        this.username = username;
        this.content = content;
    }
}
