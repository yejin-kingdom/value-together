package com.vt.valuetogether.domain.user.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateIntroduceReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdatePasswordReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.infra.mail.MailUtil;
import com.vt.valuetogether.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserTest {
    @Mock UserRepository userRepository;

    @Mock PasswordEncoder passwordEncoder;

    @Mock MailUtil mailUtil;

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
                            .username("aaa")
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
            assertEquals("username 형식에 맞지 않습니다.", exception.getResultCode().getMessage());
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
            assertEquals("password 형식에 맞지 않습니다.", exception.getResultCode().getMessage());
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
            assertEquals("email 형식에 맞지 않습니다.", exception.getResultCode().getMessage());
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

        given(userRepository.findByUsername(TEST_USER_NAME)).willReturn(TEST_USER);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            userService.signup(req);
                        });

        // then
        assertEquals("중복된 username 입니다.", exception.getResultCode().getMessage());
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

        given(userRepository.findByUsername(TEST_USER_NAME)).willReturn(null);
        given(userRepository.save(any(User.class))).willReturn(TEST_USER);
        given(mailUtil.getEmailAuth(TEST_USER_EMAIL)).willReturn(emailAuth);

        // when
        userService.signup(req);

        // then
        verify(passwordEncoder).encode(TEST_USER_PASSWORD);
        verify(userRepository).findByUsername(TEST_USER_NAME);
        verify(userRepository).save(any(User.class));

        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(TEST_USER_NAME, argumentCaptor.getValue().getUsername());
    }

    @Nested
    @DisplayName("프로필 수정")
    class profileUpdateTest {
        @Test
        @DisplayName("username 수정")
        void updateUsernameTest() {
            // given
            UserUpdateUsernameReq req = UserUpdateUsernameReq.builder()
                .userId(TEST_USER_ID)
                .username(TEST_ANOTHER_USER_NAME)
                .build();

            User user =
                User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_ANOTHER_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .email(TEST_USER_EMAIL)
                    .introduce(TEST_USER_INTRODUCE)
                    .role(Role.USER)
                    .build();

            given(userRepository.findByUserId(TEST_USER_ID)).willReturn(TEST_USER);
            given(userRepository.findByUsername(TEST_ANOTHER_USER_NAME)).willReturn(null);
            given(userRepository.save(any(User.class))).willReturn(user);

            // when
            userService.updateUsername(req);

            // then
            verify(userRepository).save(argumentCaptor.capture());
            assertEquals(TEST_ANOTHER_USER_NAME, argumentCaptor.getValue().getUsername());
        }

        @Test
        @DisplayName("introduce 수정")
        void updateIntroduceTest() {
            // given
            UserUpdateIntroduceReq req = UserUpdateIntroduceReq.builder()
                .userId(TEST_USER_ID)
                .introduce(TEST_ANOTHER_USER_INTRODUCE)
                .build();

            User user =
                User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_ANOTHER_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .email(TEST_USER_EMAIL)
                    .introduce(TEST_ANOTHER_USER_INTRODUCE)
                    .role(Role.USER)
                    .build();

            given(userRepository.findByUserId(TEST_USER_ID)).willReturn(TEST_USER);
            given(userRepository.save(any(User.class))).willReturn(user);

            // when
            userService.updateIntroduce(req);

            // then
            verify(userRepository).save(argumentCaptor.capture());
            assertEquals(TEST_ANOTHER_USER_INTRODUCE, argumentCaptor.getValue().getIntroduce());
        }

        @Test
        @DisplayName("password 수정")
        void updatePasswordTest() {
            // given
            UserUpdatePasswordReq req = UserUpdatePasswordReq.builder()
                .userId(TEST_USER_ID)
                .password(TEST_USER_PASSWORD)
                .newPassword(TEST_ANOTHER_USER_PASSWORD)
                .build();

            User TEST_USER_1 =
                User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_USER_NAME)
                    .password(passwordEncoder.encode(TEST_USER_PASSWORD))
                    .email(TEST_USER_EMAIL)
                    .introduce(TEST_USER_INTRODUCE)
                    .role(Role.USER)
                    .build();

            User TEST_USER_2 =
                User.builder()
                    .userId(TEST_USER_ID)
                    .username(TEST_USER_NAME)
                    .password(passwordEncoder.encode(TEST_ANOTHER_USER_PASSWORD))
                    .email(TEST_USER_EMAIL)
                    .introduce(TEST_USER_INTRODUCE)
                    .role(Role.USER)
                    .build();

            given(userRepository.findByUserId(TEST_USER_ID)).willReturn(TEST_USER_1);
            given(userRepository.save(any(User.class))).willReturn(TEST_USER_2);

            // when
            userService.updatePassword(req);

            // then
            verify(userRepository).save(argumentCaptor.capture());
            assertEquals(passwordEncoder.encode(TEST_ANOTHER_USER_PASSWORD), argumentCaptor.getValue().getPassword());
        }
    }
}
