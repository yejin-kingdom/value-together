package com.vt.valuetogether.domain.user.service.impl;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateProfileReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyPasswordReq;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateProfileRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyPasswordRes;
import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.user.service.UserService;
import com.vt.valuetogether.global.validator.UserValidator;
import com.vt.valuetogether.infra.mail.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailUtil mailUtil;

    private static final String EMAIL_AUTHENTICATION = "이메일 인증";

    @Override
    public UserVerifyEmailRes sendEmail(UserVerifyEmailReq req) {
        UserValidator.validate(req);

        mailUtil.sendMessage(req.getEmail(), EMAIL_AUTHENTICATION);

        return new UserVerifyEmailRes();
    }

    @Override
    public UserConfirmEmailRes confirmEmail(String email, String code) {
        mailUtil.checkCode(email, code);

        return UserConfirmEmailRes.builder().email(email).build();
    }

    @Override
    public UserSignupRes signup(UserSignupReq req) {
        UserValidator.validate(req);

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.checkDuplicatedUsername(user);

        checkAuthorizedEmail(req.getEmail());

        userRepository.save(
                User.builder()
                        .username(req.getUsername())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .email(req.getEmail())
                        .provider(Provider.LOCAL)
                        .role(Role.USER)
                        .build());

        return new UserSignupRes();
    }

    @Override
    public UserVerifyPasswordRes verifyPassword(UserVerifyPasswordReq req){
        User savedUser = getUser(req.getUserId());
        boolean isMatched = passwordEncoder.matches(req.getPassword(), savedUser.getPassword());

        return UserVerifyPasswordRes.builder().isMatched(isMatched).build();
    }

    @Override
    public UserUpdateProfileRes updateProfile(UserUpdateProfileReq req){
        User savedUser = getUser(req.getUserId());
        UserValidator.validate(req);

        if(!req.getUsername().equals(savedUser.getUsername())) {
            UserValidator.checkDuplicatedUsername(userRepository.findByUsername(req.getUsername()));
        }

        userRepository.save(
            User.builder()
                .userId(savedUser.getUserId())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(savedUser.getEmail())
                .introduce(req.getIntroduce())
                .profileImageUrl(req.getProfileImageUrl())
                .build());

        return new UserUpdateProfileRes();
    }

    private void checkAuthorizedEmail(String email) {
        EmailAuth authEmail = mailUtil.getEmailAuth(email);
        UserValidator.checkAuthorizedEmail(authEmail.isChecked());
    }

    private User getUser(Long userId){
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }

}
