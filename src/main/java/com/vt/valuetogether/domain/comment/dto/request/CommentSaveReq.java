package com.vt.valuetogether.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReq {
    private Long cardId;
    private String username;
    private String content;

    @Builder
    private CommentSaveReq(Long cardId, String username, String content) {
        this.cardId = cardId;
        this.username = username;
        this.content = content;
    }
}
