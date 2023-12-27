package com.vt.valuetogether.global.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 성공 0번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    // 기타 1000번대
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "알 수 없는 에러가 발생했습니다."),

    // 유저 2000번대
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 유저입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 2001, "잘못된 토큰"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, 2002, "만료된 토큰"),

    // 팀 3000번대
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, 3000, "존재하지 않는 팀입니다."),

    // 카테고리 4000번대

    // 카드 5000번대

    // 작업자 6000번대

    // 체크리스트 7000번대

    // 할일 8000대

    // 댓글 9000대

    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, 3001, "username 형식에 맞지 않습니다."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, 3002, "email 형식에 맞지 않습니다"),
    INVALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, 3003, "password 형식에 맞지 않습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 3004, "중복된 username 입니다."),

    ACCESS_DENY(HttpStatus.FORBIDDEN, 4000, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
