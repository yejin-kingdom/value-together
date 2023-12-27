package com.vt.valuetogether.domain.user.service.impl;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.repository.EmailAuthRepository;
import com.vt.valuetogether.domain.user.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;

    @Override
    public EmailAuth findById(String email) {
        return emailAuthRepository.findById(email);
    }

    @Override
    public Boolean hasMail(String email) {
        return emailAuthRepository.existsById(email);
    }

    @Override
    public EmailAuth save(EmailAuth emailAuth) {
        return emailAuthRepository.save(emailAuth);
    }

    @Override
    public EmailAuth delete(String email) {
        return emailAuthRepository.deleteById(email);
    }
}
