package com.vt.valuetogether.global.meta;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "알 수 없는 에러가 발생했습니다."),
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, 2000, "샘플 데이터가 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 유저입니다."),
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 팀입니다."),
    ACCESS_DENY(HttpStatus.FORBIDDEN, 4000, "접근 권한이 없습니다."),
    NOT_VALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, 3000, "username 형식에 맞지 않습니다."),
    NOT_VALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, 3000, "email 형식에 맞지 않습니다"),
    NOT_VALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, 3000, "password 형식에 맞지 않습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 3000, "중복된 username 입니다.");

    private HttpStatus status;
    private Integer code;
    private String message;

    ResultCode(HttpStatus status, Integer code, String errorMessage) {
        this.status = status;
        this.code = code;
        this.message = errorMessage;
    }
}
