package com.vt.valuetogether.domain.category.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.CategoryTest;
import com.vt.valuetogether.test.TeamRoleTest;
import com.vt.valuetogether.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest implements CategoryTest, UserTest, TeamRoleTest {
    @InjectMocks private CategoryServiceImpl categoryService;

    @Mock private CategoryRepository categoryRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private UserRepository userRepository;
    private Category category;
    private Team team;

    @BeforeEach
    void setUp() {
        team =
                Team.builder()
                        .teamId(TEST_TEAM_ID)
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .isDeleted(TEST_TEAM_IS_DELETED)
                        .teamRoleList(List.of(TEST_TEAM_ROLE))
                        .build();
        category =
                Category.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_CATEGORY_NAME)
                        .sequence(TEST_CATEGORY_SEQUENCE)
                        .isDeleted(TEST_CATEGORY_IS_DELETED)
                        .team(team)
                        .build();
    }

    @Test
    @DisplayName("category 저장 테스트")
    void category_저장() {
        // given
        Long teamId = 1L;
        String name = "category";
        CategorySaveReq categorySaveReq = CategorySaveReq.builder().teamId(teamId).name(name).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(teamRepository.findByTeamId(any())).thenReturn(team);
        when(categoryRepository.getMaxSequence(any())).thenReturn(TEST_CATEGORY_SEQUENCE);
        when(categoryRepository.save(any())).thenReturn(TEST_CATEGORY);

        // when
        categoryService.saveCategory(categorySaveReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(teamRepository).findByTeamId(any());
        verify(categoryRepository).getMaxSequence(any());
        verify(categoryRepository).save(any());
    }

    @Test
    @DisplayName("category 순서 변경 테스트")
    void category_순서_변경() {
        // given
        Long categoryId = 1L;
        Double preSequence = 2.0;
        Double postSequence = 3.0;
        CategoryChangeSequenceReq categoryChangeSequenceReq =
                CategoryChangeSequenceReq.builder()
                        .categoryId(categoryId)
                        .preSequence(preSequence)
                        .postSequence(postSequence)
                        .build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(categoryRepository.findByCategoryId(any())).thenReturn(category);
        when(categoryRepository.save(any())).thenReturn(category);

        // when
        categoryService.changeCategorySequence(categoryChangeSequenceReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(categoryRepository).findByCategoryId(any());
        verify(categoryRepository).save(any());
    }
}
