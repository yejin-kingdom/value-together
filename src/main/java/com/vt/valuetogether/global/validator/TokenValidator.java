package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.EXPIRED_TOKEN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_TOKEN;

import com.vt.valuetogether.global.exception.GlobalException;

public class TokenValidator {

    public static void checkInvalidToken(boolean isTokenValid) {
        if (!isTokenValid) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    public static void checkExpiredToken(boolean isTokenExpired) {
        if (!isTokenExpired) {
            throw new GlobalException(EXPIRED_TOKEN);
        }
    }
}
