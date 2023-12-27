package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_CODE;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_EMAIL;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.global.exception.GlobalException;

public class MailValidator {
    public static void validate(EmailAuth emailAuth) {
        if (!checkIsNull(emailAuth)) {
            throw new GlobalException(NOT_FOUND_EMAIL);
        }
    }

    public static void checkCode(String code, String inputCode) {
        if (!code.equals(inputCode)) {
            throw new GlobalException(INVALID_CODE);
        }
    }

    private static boolean checkIsNull(EmailAuth emailAuth) {
        return emailAuth == null;
    }
}
