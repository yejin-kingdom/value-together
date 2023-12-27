package com.vt.valuetogether.domain.user.service.impl;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateIntroduceReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdatePasswordReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateIntroduceRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdatePasswordRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateUsernameRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;
import com.vt.valuetogether.domain.user.entity.EmailAuth;
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

    private static final String EMAIL_AUTHORIZATION = "이메일 인증";

    @Override
    public UserVerifyEmailRes sendEmail(UserVerifyEmailReq req) {
        UserValidator.validate(req);

        mailUtil.sendMessage(req.getEmail(), EMAIL_AUTHORIZATION);

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
                        .role(Role.USER)
                        .build());

        return new UserSignupRes();
    }

    @Override
    public UserUpdateUsernameRes updateUsername(UserUpdateUsernameReq req) {
        User savedUser = getUser(req.getUserId());
        UserValidator.validate(req);

        User findUser = userRepository.findByUsername(req.getUsername());
        UserValidator.checkDuplicatedUsername(findUser);

        userRepository.save(
            User.builder()
                .userId(savedUser.getUserId())
                .username(req.getUsername())
                .password(savedUser.getPassword())
                .email(savedUser.getEmail())
                .introduce(savedUser.getIntroduce())
                .profileImageUrl(savedUser.getProfileImageUrl())
                .role(Role.USER)
                .build());

        return new UserUpdateUsernameRes();
    }

    @Override
    public UserUpdateIntroduceRes updateIntroduce(UserUpdateIntroduceReq req) {
        User savedUser = getUser(req.getUserId());

        userRepository.save(
            User.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .email(savedUser.getEmail())
                .introduce(req.getIntroduce())
                .profileImageUrl(savedUser.getProfileImageUrl())
                .build());

        return new UserUpdateIntroduceRes();
    }

    @Override
    public UserUpdatePasswordRes updatePassword(UserUpdatePasswordReq req) {
        User savedUser = getUser(req.getUserId());

        UserValidator.validate(req);
        UserValidator.verifyPassword(passwordEncoder.encode(req.getPassword()), savedUser.getPassword());

        userRepository.save(
            User.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .password(passwordEncoder.encode(req.getNewPassword()))
                .email(savedUser.getEmail())
                .introduce(savedUser.getIntroduce())
                .profileImageUrl(savedUser.getProfileImageUrl())
                .build());

        return new UserUpdatePasswordRes();
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
