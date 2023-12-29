package com.vt.valuetogether.domain.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.test.CategoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest implements CategoryTest {
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;

    @Test
    @DisplayName("category 저장 테스트")
    void category_저장() {
        // given
        teamRepository.save(TEST_TEAM);

        // when
        Category category = categoryRepository.save(TEST_CATEGORY);

        // then
        assertThat(category.getName()).isEqualTo(TEST_CATEGORY_NAME);
        assertThat(category.getSequence()).isEqualTo(TEST_CATEGORY_SEQUENCE);
        assertThat(category.getIsDeleted()).isEqualTo(TEST_CATEGORY_IS_DELETED);
    }

    @Test
    @DisplayName("teamId로 max sequence 조회 테스트")
    void teamId_max_sequence_조회() {
        // given
        Long teamId = 1L;
        teamRepository.save(TEST_TEAM);
        Category saveCategory = categoryRepository.save(TEST_CATEGORY);

        // when
        Double maxSequence = categoryRepository.getMaxSequence(teamId);

        // then
        assertThat(maxSequence).isEqualTo(saveCategory.getSequence() + 1.0);
    }
}
