package com.vt.valuetogether.domain.worker.dto.request;

import java.util.List;
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
    private List<Long> userIds;

    @Builder
    private WorkerAddReq(Long cardId, List<Long> userIds) {
        this.cardId = cardId;
        this.userIds = userIds;
    }
}
