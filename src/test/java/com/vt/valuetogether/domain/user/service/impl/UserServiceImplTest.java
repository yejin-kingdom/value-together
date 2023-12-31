package com.vt.valuetogether.domain.user.service.impl;

import static com.vt.valuetogether.global.meta.ResultCode.DUPLICATED_USERNAME;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_EMAIL_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_PASSWORD_PATTERN;
import static com.vt.valuetogether.global.meta.ResultCode.INVALID_USERNAME_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import com.vt.valuetogether.domain.user.dto.request.UserCheckDuplicateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateProfileReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyPasswordReq;
import com.vt.valuetogether.domain.user.dto.response.UserCheckDuplicateUsernameRes;
import com.vt.valuetogether.domain.user.dto.response.UserGetProfileRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyPasswordRes;
import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.infra.mail.MailUtil;
import com.vt.valuetogether.infra.s3.S3Util;
import com.vt.valuetogether.infra.s3.S3Util.FilePath;
import com.vt.valuetogether.test.UserTest;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserTest {

    @Mock UserRepository userRepository;

    @Mock PasswordEncoder passwordEncoder;

    @Mock MailUtil mailUtil;

    @Mock S3Util s3Util;

    @InjectMocks UserServiceImpl userService;

    @Captor ArgumentCaptor<User> argumentCaptor;

    @Nested
    @DisplayName("이메일 인증 관련 테스트")
    class emailAuthorization {

        @Test
        @DisplayName("메일 전송")
        void sendEmailTest() {
            // given
            UserVerifyEmailReq req = UserVerifyEmailReq.builder().email(TEST_USER_EMAIL).build();

            // when
            userService.sendEmail(req);

            // then
            verify(mailUtil).sendMessage(TEST_USER_EMAIL, "이메일 인증");
        }

        @Test
        @DisplayName("메일 코드 확인")
        void confirmEmailTest() {
            // given
            String code = "code";

            // when
            userService.confirmEmail(TEST_USER_EMAIL, code);

            // then
            verify(mailUtil).checkCode(TEST_USER_EMAIL, code);
        }
    }

    @Nested
    @DisplayName("회원가입 - req 검증")
    class invalidInputTest {

        @Test
        @DisplayName("username 검증")
        void usernameTest() {
            // given
            UserSignupReq req =
                    UserSignupReq.builder()
                            .username("a")
                            .password(TEST_USER_PASSWORD)
                            .email(TEST_USER_EMAIL)
                            .build();

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userService.signup(req);
                            });

            // then
            assertEquals(INVALID_USERNAME_PATTERN.getMessage(), exception.getResultCode().getMessage());
        }

        @Test
        @DisplayName("password 검증")
        void passwordTest() {
            // given
            UserSignupReq req =
                    UserSignupReq.builder()
                            .username(TEST_USER_NAME)
                            .password("aaa")
                            .email(TEST_USER_EMAIL)
                            .build();

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userService.signup(req);
                            });

            // then
            assertEquals(INVALID_PASSWORD_PATTERN.getMessage(), exception.getResultCode().getMessage());
        }

        @Test
        @DisplayName("email 검증")
        void emailTest() {
            // given
            UserSignupReq req =
                    UserSignupReq.builder()
                            .username(TEST_USER_NAME)
                            .password(TEST_USER_PASSWORD)
                            .email("aaa@aa")
                            .build();

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userService.signup(req);
                            });

            // then
            assertEquals(INVALID_EMAIL_PATTERN.getMessage(), exception.getResultCode().getMessage());
        }
    }

    @Test
    @DisplayName("회원가입 - 중복 회원 존재")
    void duplicatedUsernameTest() {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .email(TEST_USER_EMAIL)
                        .build();

        given(userRepository.existsByUsername(req.getUsername())).willReturn(true);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            userService.signup(req);
                        });

        // then
        assertEquals(DUPLICATED_USERNAME.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("회원가입")
    void signupTest() {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .email(TEST_USER_EMAIL)
                        .build();

        EmailAuth emailAuth =
                EmailAuth.builder().email(TEST_USER_EMAIL).code("aaa").isChecked(true).build();

        given(userRepository.existsByUsername(TEST_USER_NAME)).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(TEST_USER);
        given(mailUtil.getEmailAuth(TEST_USER_EMAIL)).willReturn(emailAuth);

        // when
        userService.signup(req);

        // then
        verify(passwordEncoder).encode(TEST_USER_PASSWORD);
        verify(userRepository).existsByUsername(TEST_USER_NAME);
        verify(userRepository).save(any(User.class));

        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(TEST_USER_NAME, argumentCaptor.getValue().getUsername());
    }

    @Test
    @DisplayName("사용자 이름 중복 확인")
    void checkDuplicateUsernameTest() {
        // given
        UserCheckDuplicateUsernameReq req =
                UserCheckDuplicateUsernameReq.builder().username(TEST_ANOTHER_USER_NAME).build();

        given(userRepository.existsByUsername(req.getUsername())).willReturn(true);

        // when
        UserCheckDuplicateUsernameRes res = userService.checkDuplicateUsername(req);

        // then
        assertTrue(res.isDuplicated());
    }

    @Test
    @DisplayName("비밀번호 확인 - 일치")
    void verifyPasswordTest() {
        // given
        UserVerifyPasswordReq req =
                UserVerifyPasswordReq.builder()
                        .username(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();

        given(userRepository.findByUsername(req.getUsername())).willReturn(TEST_USER);
        given(passwordEncoder.matches(req.getPassword(), TEST_USER_PASSWORD))
                .willReturn(req.getPassword().equals(TEST_USER_PASSWORD));

        // when
        UserVerifyPasswordRes res = userService.verifyPassword(req);

        // then
        assertTrue(res.isMatched());
    }

    @Test
    @DisplayName("프로필 수정")
    void updateProfileTest() throws IOException {
        // given
        UserUpdateProfileReq req =
                UserUpdateProfileReq.builder()
                        .preUsername(TEST_USER_NAME)
                        .username(TEST_ANOTHER_USER_NAME)
                        .password(TEST_ANOTHER_USER_PASSWORD)
                        .introduce(TEST_ANOTHER_USER_INTRODUCE)
                        .build();

        String imageUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        MultipartFile multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());

        User UPDATED_USER =
                User.builder()
                        .userId(TEST_USER_ID)
                        .username(TEST_ANOTHER_USER_NAME)
                        .password(TEST_ANOTHER_USER_PASSWORD)
                        .email(TEST_USER_EMAIL)
                        .introduce(TEST_ANOTHER_USER_INTRODUCE)
                        .profileImageUrl(TEST_ANOTHER_USER_PROFILE_URL)
                        .role(Role.USER)
                        .build();

        given(userRepository.findByUsername(req.getPreUsername())).willReturn(TEST_USER);
        given(passwordEncoder.encode(req.getPassword())).willReturn(req.getPassword());
        given(s3Util.uploadFile(multipartFile, FilePath.PROFILE))
                .willReturn(TEST_ANOTHER_USER_PROFILE_URL);
        given(userRepository.save(any(User.class))).willReturn(UPDATED_USER);

        // when
        userService.updateProfile(req, multipartFile);

        // then
        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(TEST_ANOTHER_USER_NAME, argumentCaptor.getValue().getUsername());
        assertEquals(TEST_ANOTHER_USER_PASSWORD, argumentCaptor.getValue().getPassword());
        assertEquals(TEST_ANOTHER_USER_INTRODUCE, argumentCaptor.getValue().getIntroduce());
        assertEquals(TEST_ANOTHER_USER_PROFILE_URL, argumentCaptor.getValue().getProfileImageUrl());
    }

    @Test
    @DisplayName("프로필 조회")
    void getProfileTest() {
        // given
        given(userRepository.findByUserId(TEST_USER_ID)).willReturn(TEST_USER);

        // when
        UserGetProfileRes res = userService.getProfile(TEST_USER_ID);

        // then
        assertEquals(TEST_USER_NAME, res.getUsername());
        assertEquals(TEST_USER_EMAIL, res.getEmail());
        assertEquals(TEST_USER_INTRODUCE, res.getIntroduce());
    }
}
