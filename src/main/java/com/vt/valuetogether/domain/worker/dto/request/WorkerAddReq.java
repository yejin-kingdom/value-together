package com.vt.valuetogether.domain.worker.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerAddReq {

    private Long cardId;
    private String username;
    private String addUsername;

    @Builder
    private WorkerAddReq(Long cardId, String addUsername) {
        this.cardId = cardId;
        this.addUsername = addUsername;
    }
}
