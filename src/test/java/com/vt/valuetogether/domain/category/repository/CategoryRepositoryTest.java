package com.vt.valuetogether.domain.category.repository;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.test.CategoryTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest implements CategoryTest {
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;
    private Team saveTeam;
    private Category saveCategory1;
    private Category saveCategory2;

    @BeforeEach
    void setUp() {
        saveTeam = teamRepository.save(TEST_TEAM);
        saveCategory1 =
                categoryRepository.save(
                        Category.builder()
                                .categoryId(TEST_CATEGORY_ID)
                                .name(TEST_CATEGORY_NAME)
                                .sequence(TEST_CATEGORY_SEQUENCE)
                                .isDeleted(TEST_CATEGORY_IS_DELETED)
                                .team(saveTeam)
                                .build());

        saveCategory2 =
                categoryRepository.save(
                        Category.builder()
                                .categoryId(TEST_ANOTHER_CATEGORY_ID)
                                .name(TEST_ANOTHER_CATEGORY_NAME)
                                .sequence(TEST_ANOTHER_CATEGORY_SEQUENCE)
                                .isDeleted(TEST_ANOTHER_CATEGORY_IS_DELETED)
                                .team(saveTeam)
                                .build());
    }

    @Test
    @DisplayName("category 저장 테스트")
    void category_저장() {
        // given

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

        // when
        Double maxSequence = categoryRepository.getMaxSequence(saveCategory2.getTeam().getTeamId());

        // then
        assertThat(maxSequence).isEqualTo(saveCategory2.getSequence() + 1.0);
    }

    @Test
    @DisplayName("id로 category 조회 테스트")
    void id_category_조회() {
        // given

        // when
        Category category = categoryRepository.findByCategoryId(saveCategory1.getCategoryId());

        // then
        assertThat(category.getName()).isEqualTo(saveCategory1.getName());
        assertThat(category.getSequence()).isEqualTo(saveCategory1.getSequence());
        assertThat(category.getIsDeleted()).isEqualTo(saveCategory1.getIsDeleted());
    }

    @Test
    @DisplayName("teamId로 category 전체 조회 테스트")
    void teamId_category_전체_조회() {
        // given

        // when
        List<Category> categories =
                categoryRepository.findByTeamTeamIdOrderBySequenceAsc(saveTeam.getTeamId());

        // then
        assertThat(categories.get(0)).isEqualTo(saveCategory1);
        assertThat(categories.get(1)).isEqualTo(saveCategory2);
    }

    @Test
    @DisplayName("teamId별 category list 조회 테스트")
    void teamId_category_list_조회() {
        // given

        // when
        List<Category> categories = categoryRepository.findByOrderByTeamTeamIdAscSequenceAsc();

        // then
        assertThat(categories.get(0)).isEqualTo(saveCategory1);
        assertThat(categories.get(1)).isEqualTo(saveCategory2);
    }

    @Test
    @DisplayName("categories 저장 테스트")
    void categories_저장() {
        // given
        List<Category> categories = List.of(TEST_CATEGORY, TEST_ANOTHER_CATEGORY);

        // when
        List<Category> saveCategories = categoryRepository.saveAll(categories);

        // then
        assertThat(saveCategories.get(0).getName()).isEqualTo(categories.get(0).getName());
        assertThat(saveCategories.get(0).getSequence()).isEqualTo(categories.get(0).getSequence());
        assertThat(saveCategories.get(0).getIsDeleted()).isEqualTo(categories.get(0).getIsDeleted());

        assertThat(saveCategories.get(1).getName()).isEqualTo(categories.get(1).getName());
        assertThat(saveCategories.get(1).getSequence()).isEqualTo(categories.get(1).getSequence());
        assertThat(saveCategories.get(1).getIsDeleted()).isEqualTo(categories.get(1).getIsDeleted());
    }

    @Test
    @DisplayName("categories 삭제 테스트")
    void categories_삭제() {
        // given
        Boolean isDeleted = TRUE;
        ZonedDateTime dateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime = dateTime.toLocalDate().plusDays(1L).atStartOfDay();
        Category saveCategory3 =
                Category.builder()
                        .categoryId(3L)
                        .name(TEST_ANOTHER_CATEGORY_NAME)
                        .sequence(TEST_ANOTHER_CATEGORY_SEQUENCE)
                        .isDeleted(TRUE)
                        .team(saveTeam)
                        .build();
        saveCategory3 = categoryRepository.save(saveCategory3);
        ReflectionTestUtils.setField(saveCategory3, "modifiedAt", LocalDateTime.now());

        // when
        categoryRepository.deleteByIsDeletedAndModifiedAtBefore(isDeleted, localDateTime);
        Category category = categoryRepository.findByCategoryId(saveCategory3.getCategoryId());

        // then
        assertThat(category).isNull();
    }
}
