package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.user.entity.EmailAuth;

public interface EmailAuthTest {
    String TEST_EMAIL = "test@gmail.com";

    String TEST_CODE = "ABC123";

    EmailAuth TEST_EMAIL_AUTH =
            EmailAuth.builder().email(TEST_EMAIL).code(TEST_CODE).isChecked(false).build();
}
