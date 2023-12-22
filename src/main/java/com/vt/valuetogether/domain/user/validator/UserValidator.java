package com.vt.valuetogether.domain.user.validator;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.entity.User;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator {

    private static final String USERNAME_REGEX = "^[0-9a-zA-z]{4,15}$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-z$@!%*#?&]{8,15}$";
    private static final String EMAIL_REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?";

    public static void validate(UserSignupReq req) {
        if (!Pattern.matches(USERNAME_REGEX, req.getUsername())) {
            throw new IllegalArgumentException("username 형식에 맞지 않습니다.");
        }
        ;

        if (!Pattern.matches(PASSWORD_REGEX, req.getPassword())) {
            throw new IllegalArgumentException("password 형식에 맞지 않습니다.");
        }

        if (!Pattern.matches(EMAIL_REGEX, req.getEmail())) {
            throw new IllegalArgumentException("email 형식에 맞지 않습니다.");
        }
    }

    public static void checkDuplicatedUsername(User user) {
        if (!checkIsNull(user)) {
            throw new IllegalArgumentException("중복된 username입니다.");
        }
    }

    private static boolean checkIsNull(User user) {
        return user == null;
    }
}
