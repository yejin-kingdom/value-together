package com.vt.valuetogether.global.meta;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "알 수 없는 애러가 발생했습니다."),
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, 2000, "샘플 데이터가 없습니다.");

    private HttpStatus status;
    private Integer code;
    private String message;

    ResultCode(HttpStatus status, Integer code, String errorMessage) {
        this.status = status;
        this.code = code;
        this.message = errorMessage;
    }
}
