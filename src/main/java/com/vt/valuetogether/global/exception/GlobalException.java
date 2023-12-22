package com.vt.valuetogether.global.exception;

import com.vt.valuetogether.global.meta.ResultCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
