package com.vt.valuetogether.domain.worker.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerAddRes {

    private Long workerId;

    @Builder
    private WorkerAddRes(Long workerId) {
        this.workerId = workerId;
    }
}
