package com.vt.valuetogether.global.exception;

import com.vt.valuetogether.global.response.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public RestResponse<Void> handleException(GlobalException e) {
        return RestResponse.error(e.getResultCode());
    }
}
