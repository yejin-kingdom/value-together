package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_TOKEN;
import static com.vt.valuetogether.global.meta.ResultCode.LOGIN_REQUIRED;

import com.vt.valuetogether.global.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "token validator")
public class TokenValidator {

    public static void checkValidToken(boolean isTokenValid) {
        if (!isTokenValid) {
            log.info("invalid token");
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    public static void checkLoginRequired(boolean isLoginRequired) {
        if (isLoginRequired) {
            log.info("login required");
            throw new GlobalException(LOGIN_REQUIRED);
        }
    }
}
