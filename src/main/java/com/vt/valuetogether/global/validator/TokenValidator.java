package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_TOKEN;
import static com.vt.valuetogether.global.meta.ResultCode.LOGIN_REQUIRED;

import com.vt.valuetogether.global.exception.GlobalException;

public class TokenValidator {

    public static void checkValidToken(boolean isTokenValid) {
        if (!isTokenValid) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    public static void checkLoginRequired(boolean isLoginRequired) {
        if (isLoginRequired) {
            throw new GlobalException(LOGIN_REQUIRED);
        }
    }
}
