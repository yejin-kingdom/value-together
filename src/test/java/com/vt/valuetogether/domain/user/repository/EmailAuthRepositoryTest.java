package com.vt.valuetogether.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.test.EmailAuthTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@DataRedisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmailAuthRepositoryTest implements EmailAuthTest {

    @Autowired private EmailAuthRepository emailAuthRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        EmailAuth saveEmailAuth = emailAuthRepository.save(TEST_EMAIL_AUTH);

        // then
        assertEquals(TEST_EMAIL_AUTH.getEmail(), saveEmailAuth.getEmail());
        assertEquals(TEST_EMAIL_AUTH.getCode(), saveEmailAuth.getCode());
        assertEquals(TEST_EMAIL_AUTH.isChecked(), saveEmailAuth.isChecked());
    }

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        emailAuthRepository.save(TEST_EMAIL_AUTH);

        // when
        EmailAuth findEmailAuth = emailAuthRepository.findById(TEST_EMAIL);

        // then
        assertEquals(TEST_EMAIL_AUTH.getEmail(), findEmailAuth.getEmail());
        assertEquals(TEST_EMAIL_AUTH.getCode(), findEmailAuth.getCode());
        assertEquals(TEST_EMAIL_AUTH.isChecked(), findEmailAuth.isChecked());
    }

    @Test
    @DisplayName("existsById 테스트")
    void existsByIdTest() {
        // given
        emailAuthRepository.save(TEST_EMAIL_AUTH);

        // when
        boolean exists = emailAuthRepository.existsById(TEST_EMAIL);

        // then
        assertTrue(exists);
    }

    @Test
    @DisplayName("deleteById 테스트")
    void deleteByIdTest() {
        // given
        emailAuthRepository.save(TEST_EMAIL_AUTH);

        // when
        emailAuthRepository.deleteById(TEST_EMAIL);
        EmailAuth findEmailAuth = emailAuthRepository.findById(TEST_EMAIL);

        // then
        assertNull(findEmailAuth);
    }
}
