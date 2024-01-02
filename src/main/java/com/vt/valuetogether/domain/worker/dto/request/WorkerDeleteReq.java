package com.vt.valuetogether.domain.worker.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerDeleteReq {

    private Long cardId;
    private String username;
    private String deleteUsername;

    @Builder
    private WorkerDeleteReq(Long cardId, String deleteUsername) {
        this.cardId = cardId;
        this.deleteUsername = deleteUsername;
    }
}
