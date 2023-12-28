package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;

public interface UserTest {

    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;

    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "ABcd5678#&";
    String TEST_USER_EMAIL = "username@gmail.com";
    String TEST_USER_INTRODUCE = "introduce";
    String TEST_USER_PROFILE_URL = "resources/profile/image1.png";

    String TEST_ANOTHER_USER_NAME = "another";
    String TEST_ANOTHER_USER_PASSWORD = "ABcd56@8";
    String TEST_ANOTHER_USER_EMAIL = "another@gmail.com";
    String TEST_ANOTHER_USER_INTRODUCE = "another introduce";
    String TEST_ANOTHER_USER_PROFILE_URL = "resources/profile/image2.png";

    User TEST_USER =
            User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .email(TEST_USER_EMAIL)
                    .introduce(TEST_USER_INTRODUCE)
                    .profileImageUrl(TEST_USER_PROFILE_URL)
                    .role(Role.USER)
                    .build();
    User ANOTHER_TEST_USER =
            User.builder()
                    .userId(TEST_ANOTHER_USER_ID)
                    .username(TEST_ANOTHER_USER_NAME)
                    .password(TEST_ANOTHER_USER_PASSWORD)
                    .email(TEST_ANOTHER_USER_EMAIL)
                    .introduce(TEST_ANOTHER_USER_INTRODUCE)
                    .profileImageUrl(TEST_ANOTHER_USER_PROFILE_URL)
                    .build();
}
