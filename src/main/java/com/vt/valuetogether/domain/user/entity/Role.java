package com.vt.valuetogether.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("ADMIN", "관리자"),
    ROLE_USER("USER", "사용자");

    private final String authority;

    private final String value;
}
