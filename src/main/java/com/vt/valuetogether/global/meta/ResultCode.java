package com.vt.valuetogether.global.meta;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1001, "알 수 없는 에러가 발생했습니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 1002, "이메일 전송에 실패하였습니다."),

    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, 2001, "샘플 데이터가 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 2002, "존재하지 않는 유저입니다."),
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, 2003, "존재하지 않는 팀입니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, 2004, "해당 이메일을 찾을 수 없습니다."),

    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, 3001, "username 형식에 맞지 않습니다."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, 3002, "email 형식에 맞지 않습니다."),
    INVALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, 3003, "password 형식에 맞지 않습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 3004, "중복된 username 입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, 3005, "인증코드가 일치하지 않습니다."),

    ACCESS_DENY(HttpStatus.FORBIDDEN, 4001, "접근 권한이 없습니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, 4002, "인증 완료되지 않은 email 입니다.");

    private HttpStatus status;
    private Integer code;
    private String message;

    ResultCode(HttpStatus status, Integer code, String errorMessage) {
        this.status = status;
        this.code = code;
        this.message = errorMessage;
    }
}
