package com.vt.valuetogether.domain.user.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.repository.EmailAuthRepository;
import com.vt.valuetogether.test.EmailAuthTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailAuthServiceImplTest implements EmailAuthTest {
    @Mock EmailAuthRepository emailAuthRepository;

    @InjectMocks EmailAuthServiceImpl emailAuthService;

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        given(emailAuthRepository.findById(TEST_EMAIL)).willReturn(TEST_EMAIL_AUTH);

        // when
        EmailAuth emailAuth = emailAuthService.findById(TEST_EMAIL);

        // then
        assertEquals(TEST_CODE, emailAuth.getCode());
    }

    @Test
    @DisplayName("hasMail 테스트")
    void hasMailTest() {
        // given
        given(emailAuthRepository.existsById(TEST_EMAIL)).willReturn(true);

        // when
        boolean hasMail = emailAuthService.hasMail(TEST_EMAIL);

        // then
        assertTrue(hasMail);
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        given(emailAuthRepository.save(TEST_EMAIL_AUTH)).willReturn(TEST_EMAIL_AUTH);

        // when
        EmailAuth emailAuth = emailAuthService.save(TEST_EMAIL_AUTH);

        // then
        assertEquals(TEST_EMAIL_AUTH.getEmail(), emailAuth.getEmail());
        assertEquals(TEST_EMAIL_AUTH.getCode(), emailAuth.getCode());
        assertEquals(TEST_EMAIL_AUTH.isChecked(), emailAuth.isChecked());
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        given(emailAuthRepository.deleteById(TEST_EMAIL)).willReturn(null);

        // when
        EmailAuth emailAuth = emailAuthService.delete(TEST_EMAIL);

        // then
        assertNull(emailAuth);
    }
}
