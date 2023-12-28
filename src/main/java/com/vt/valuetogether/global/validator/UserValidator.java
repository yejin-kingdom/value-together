package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.DUPLICATED_USERNAME;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_EMAIL_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_PASSWORD_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_USERNAME_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_USER;
import static com.vt.valuetogether.global.meta.ResultCode.UNAUTHORIZED_EMAIL;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String USERNAME_REGEX = "^[0-9a-zA-z]{4,15}$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-z$@!%*#?&]{8,15}$";
    private static final String EMAIL_REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?";

    public static void validate(UserVerifyEmailReq req) {
        if (!checkIsValidateEmail(req.getEmail())) {
            throw new GlobalException(INVALID_EMAIL_PATTERN);
        }
    }

    public static void validate(UserSignupReq req) {
        if (!checkIsValidateUsername(req.getUsername())) {
            throw new GlobalException(INVALID_USERNAME_PATTERN);
        }
        if (!checkIsValidatePassword(req.getPassword())) {
            throw new GlobalException(INVALID_PASSWORD_PATTERN);
        }
        if (!checkIsValidateEmail(req.getEmail())) {
            throw new GlobalException(INVALID_EMAIL_PATTERN);
        }
    }

    public static void validate(User user) {
        if (!checkIsNull(user)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    public static void checkDuplicatedUsername(User user) {
        if (!checkIsNull(user)) {
            throw new GlobalException(DUPLICATED_USERNAME);
        }
    }

    public static void checkAuthorizedEmail(boolean isChecked) {
        if (!isChecked) {
            throw new GlobalException(UNAUTHORIZED_EMAIL);
        }
    }

    public static void checkExistingUsername(User user) {
        if (checkIsNull(user)) {
            throw new GlobalException(ResultCode.NOT_FOUND_USER);
        }
    }

    private static boolean checkIsNull(User user) {
        return user == null;
    }

    private static boolean checkIsValidateUsername(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    private static boolean checkIsValidatePassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    private static boolean checkIsValidateEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
