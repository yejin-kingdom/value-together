package com.vt.valuetogether.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "사용자");

    private final String code;

    private final String value;
}
