package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.DUPLICATED_USERNAME;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_EMAIL_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_PASSWORD_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_USERNAME_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.MISMATCHED_PASSWORD;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_USER;
import static com.vt.valuetogether.global.meta.ResultCode.UNAUTHORIZED_EMAIL;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdatePasswordReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.global.exception.GlobalException;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static void validate(UserUpdateUsernameReq req){
        if (!checkIsValidateUsername(req.getUsername())) {
            throw new GlobalException(INVALID_USERNAME_PATTERN);
        }
    }

    public static void validate(UserUpdatePasswordReq req){
        if (!checkIsValidatePassword(req.getNewPassword())) {
            throw new GlobalException(INVALID_PASSWORD_PATTERN);
        }
    }

    public static void validate(User user){
        if(checkIsNull(user)){
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

    public static void verifyPassword(String inputPassword, String password) {
        if (!isCollectPassword(inputPassword, password)) {
            throw new GlobalException(MISMATCHED_PASSWORD);
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

    private static boolean isCollectPassword(String inputPassword, String password){
        return Objects.equals(inputPassword, password);
    }
}
