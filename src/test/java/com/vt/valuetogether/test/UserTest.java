package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.user.entity.User;

public interface UserTest {

    Long TEST_USER_ID = 1L;
    Long ANOTHER_TEST_USER_ID = 2L;

    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "12345678";
    String TEST_USER_EMAIL = "username@gmail.com";

    String TEST_ANOTHER_USER_NAME = "another";
    String TEST_ANOTHER_USER_PASSWORD = "12345678";
    String TEST_ANOTHER_USER_EMAIL = "another@gmail.com";

    User TEST_USER =
            User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .email(TEST_USER_EMAIL)
                    .build();
    User ANOTHER_TEST_USER =
            User.builder()
                    .userId(ANOTHER_TEST_USER_ID)
                    .username(TEST_ANOTHER_USER_NAME)
                    .password(TEST_ANOTHER_USER_PASSWORD)
                    .email(TEST_ANOTHER_USER_EMAIL)
                    .build();
}
