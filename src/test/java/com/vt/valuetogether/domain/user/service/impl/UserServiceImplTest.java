package com.vt.valuetogether.domain.user.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.entity.EmailAuth;
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
}
