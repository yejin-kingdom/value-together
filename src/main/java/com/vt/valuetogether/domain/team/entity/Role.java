package com.vt.valuetogether.domain.team.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    LEADER("LEADER", "팀장"),
    MEMBER("MEMBER", "팀원");

    private final String authority;

    private final String value;
}
