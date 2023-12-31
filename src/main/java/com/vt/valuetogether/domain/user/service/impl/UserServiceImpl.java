package com.vt.valuetogether.domain.user.service.impl;

import com.vt.valuetogether.domain.user.dto.request.UserCheckDuplicateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateProfileReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyPasswordReq;
import com.vt.valuetogether.domain.user.dto.response.UserCheckDuplicateUsernameRes;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserGetProfileRes;
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
import com.vt.valuetogether.domain.user.service.UserServiceMapper;
import com.vt.valuetogether.global.validator.S3Validator;
import com.vt.valuetogether.global.validator.UserValidator;
import com.vt.valuetogether.infra.mail.MailUtil;
import com.vt.valuetogether.infra.s3.S3Util;
import com.vt.valuetogether.infra.s3.S3Util.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String EMAIL_AUTHENTICATION = "이메일 인증";
    private static final String DEFAULT_PROFILE_INTRODUCE = "자기소개를 입력해주세요.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailUtil mailUtil;
    private final S3Util s3Util;

    @Value("${default.image.address}")
    private String defaultProfileImageUrl;

    @Override
    public UserGetProfileRes getProfile(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);

        return UserServiceMapper.INSTANCE.toUserGetProfileRes(user);
    }

    @Override
    public UserConfirmEmailRes confirmEmail(String email, String code) {
        mailUtil.checkCode(email, code);

        return UserConfirmEmailRes.builder().email(email).build();
    }

    @Override
    public UserSignupRes signup(UserSignupReq req) {
        UserValidator.validate(req);

        UserValidator.checkDuplicatedUsername(userRepository.existsByUsername(req.getUsername()));

        checkAuthorizedEmail(req.getEmail());
        UserValidator.checkDuplicatedEmail(
                userRepository.findByEmailAndProvider(req.getEmail(), Provider.LOCAL));

        userRepository.save(
                User.builder()
                        .username(req.getUsername())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .email(req.getEmail())
                        .introduce(DEFAULT_PROFILE_INTRODUCE)
                        .profileImageUrl(defaultProfileImageUrl)
                        .provider(Provider.LOCAL)
                        .role(Role.USER)
                        .build());

        return new UserSignupRes();
    }

    @Override
    public UserVerifyEmailRes sendEmail(UserVerifyEmailReq req) {
        UserValidator.validate(req);

        mailUtil.sendMessage(req.getEmail(), EMAIL_AUTHENTICATION);

        return new UserVerifyEmailRes();
    }

    @Override
    public UserCheckDuplicateUsernameRes checkDuplicateUsername(UserCheckDuplicateUsernameReq req) {
        UserValidator.validate(req);

        return UserCheckDuplicateUsernameRes.builder()
                .isDuplicated(userRepository.existsByUsername(req.getUsername()))
                .build();
    }

    @Override
    public UserVerifyPasswordRes verifyPassword(UserVerifyPasswordReq req) {
        User savedUser = getUser(req.getUsername());
        boolean isMatched = passwordEncoder.matches(req.getPassword(), savedUser.getPassword());

        return UserVerifyPasswordRes.builder().isMatched(isMatched).build();
    }

    @Override
    public UserUpdateProfileRes updateProfile(UserUpdateProfileReq req, MultipartFile multipartFile) {

        UserValidator.validate(req);
        User savedUser = getUser(req.getUsername());

        String imageUrl = savedUser.getProfileImageUrl();
        if (!imageUrl.equals(defaultProfileImageUrl)) {
            s3Util.deleteFile(imageUrl, FilePath.PROFILE);
        }
        if (multipartFile == null || multipartFile.isEmpty()) {
            imageUrl = defaultProfileImageUrl;
        } else {
            S3Validator.isProfileImageFile(multipartFile);
            imageUrl = s3Util.uploadFile(multipartFile, FilePath.PROFILE);
        }

        userRepository.save(
                User.builder()
                        .userId(savedUser.getUserId())
                        .username(savedUser.getUsername())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .email(savedUser.getEmail())
                        .introduce(req.getIntroduce())
                        .profileImageUrl(imageUrl)
                        .provider(savedUser.getProvider())
                        .role(savedUser.getRole())
                        .build());

        return new UserUpdateProfileRes();
    }

    private void checkAuthorizedEmail(String email) {
        EmailAuth authEmail = mailUtil.getEmailAuth(email);
        UserValidator.checkAuthorizedEmail(authEmail.isChecked());
    }

    private User getUser(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }
}
