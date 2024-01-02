package com.vt.valuetogether.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest implements UserTest {

    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        User saveUser = userRepository.save(TEST_USER);

        // then
        assertEquals(saveUser.getUsername(), TEST_USER_NAME);
        assertEquals(saveUser.getEmail(), TEST_USER_EMAIL);
        assertEquals(saveUser.getProfileImageUrl(), TEST_USER_PROFILE_URL);
    }

    @Test
    @DisplayName("username으로 User 조회")
    void findByUsernameTest() {
        // given
        User saveUser = userRepository.save(TEST_USER);

        // when
        User user = userRepository.findByUsername(saveUser.getUsername());

        // then
        assertEquals(user.getUserId(), saveUser.getUserId());
        assertEquals(user.getUsername(), saveUser.getUsername());
        assertEquals(user.getEmail(), saveUser.getEmail());
        assertEquals(user.getProfileImageUrl(), saveUser.getProfileImageUrl());
    }

    @Test
    @DisplayName("userId로 User 조회")
    void findByUserIdTest() {
        // given
        User saveUser = userRepository.save(TEST_USER);

        // when
        User user = userRepository.findByUserId(saveUser.getUserId());

        // then
        assertEquals(user.getUserId(), saveUser.getUserId());
        assertEquals(user.getUsername(), saveUser.getUsername());
        assertEquals(user.getEmail(), saveUser.getEmail());
        assertEquals(user.getProfileImageUrl(), saveUser.getProfileImageUrl());
    }

    @Test
    @DisplayName("username 중복 확인")
    void existsByUsernameTest() {
        // given
        User saveUser = userRepository.save(TEST_USER);

        // when
        boolean isDuplicated = userRepository.existsByUsername(saveUser.getUsername());

        // then
        assertTrue(isDuplicated);
    }

    @Test
    @DisplayName("유저 이름 리스트로 유저 조회하기")
    void findAllByUsernameIn() {
        // given
        userRepository.save(TEST_USER);

        List<String> nameList = List.of(TEST_USER_NAME, "test2");

        // when
        List<User> actual = userRepository.findAllByUsernameIn(nameList);

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getUsername()).isEqualTo(TEST_USER_NAME);
    }
}
