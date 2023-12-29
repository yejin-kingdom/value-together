package com.vt.valuetogether.domain.worker.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerDeleteReq {

    private Long workerId;

    @Builder
    private WorkerDeleteReq(Long workerId) {
        this.workerId = workerId;
    }
}
