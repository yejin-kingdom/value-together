package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.entity.EmailAuth;

public interface EmailAuthService {
    EmailAuth findById(String email);

    Boolean hasMail(String email);

    EmailAuth save(EmailAuth emailAuth);

    EmailAuth delete(String email);
}
