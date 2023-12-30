package com.vt.valuetogether.domain.worker.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerGetRes {
    private String username;
    private String profileImageUrl;

    @Builder
    private WorkerGetRes(String username, String profileImageUrl) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }
}
