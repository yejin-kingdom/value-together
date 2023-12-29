package com.vt.valuetogether.domain.worker.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerAddRes {

    private Long workId;

    @Builder
    private WorkerAddRes(Long workId) {
        this.workId = workId;
    }
}
