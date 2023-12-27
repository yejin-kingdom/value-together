package com.vt.valuetogether.global.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 성공 0번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    // 글로벌 1000번대
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "알 수 없는 에러가 발생했습니다."),
    ACCESS_DENY(HttpStatus.FORBIDDEN, 1001, "접근 권한이 없습니다."),

    // 유저 2000번대
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 유저입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 2001, "잘못된 토큰"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, 2002, "만료된 토큰"),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 2003, "이메일 전송에 실패하였습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, 2004, "해당 이메일을 찾을 수 없습니다."),
    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, 2005, "username 형식에 맞지 않습니다."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, 2006, "email 형식에 맞지 않습니다."),
    INVALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, 2007, "password 형식에 맞지 않습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 2008, "중복된 username 입니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, 2009, "인증 완료되지 않은 email 입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, 2010, "인증코드가 일치하지 않습니다."),

    // 팀 3000번대
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, 3000, "존재하지 않는 팀입니다.");

    // 카테고리 4000번대

    // 카드 5000번대

    // 작업자 6000번대

    // 체크리스트 7000번대

    // 할일 8000대

    // 댓글 9000대

    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
