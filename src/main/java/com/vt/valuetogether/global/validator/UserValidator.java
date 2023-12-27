package com.vt.valuetogether.global.validator;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String USERNAME_REGEX = "^[0-9a-zA-z]{4,15}$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-z$@!%*#?&]{8,15}$";
    private static final String EMAIL_REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?";

    public static void validate(UserSignupReq req) {
        if (!checkIsValidateUsername(req.getUsername())) {
            throw new IllegalArgumentException("username 형식에 맞지 않습니다.");
        }
        if (!checkIsValidatePassword(req.getPassword())) {
            throw new IllegalArgumentException("password 형식에 맞지 않습니다.");
        }
        if (!checkIsValidateEmail(req.getEmail())) {
            throw new IllegalArgumentException("email 형식에 맞지 않습니다.");
        }
    }

    public static void checkDuplicatedUsername(User user) {
        if (!checkIsNull(user)) {
            throw new IllegalArgumentException("중복된 username입니다.");
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
