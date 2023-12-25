package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import java.util.Optional;

public interface EmailAuthService {
    Optional<EmailAuth> findById(String email);

    Boolean hasMail(String email);

    EmailAuth save(EmailAuth emailAuth);

    void delete(String email);
}
