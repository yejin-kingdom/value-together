package com.vt.valuetogether.domain.category.service.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryRestoreReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest implements CategoryTest, UserTest, TeamRoleTest {

    @Captor ArgumentCaptor<Category> argumentCaptor;
    @InjectMocks private CategoryServiceImpl categoryService;
    @Mock private CategoryRepository categoryRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private UserRepository userRepository;
    private List<Category> categories;
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
        categories = List.of(category);
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

    @Test
    @DisplayName("teamId로 category 전체 조회 테스트")
    void teamId_category_전체_조회() {
        // given
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(teamRepository.findByTeamId(any())).thenReturn(team);
        when(categoryRepository.findByTeamTeamIdAndIsDeletedOrderBySequenceAsc(any(), any()))
                .thenReturn(categories);

        // when
        CategoryGetResList categoryGetResList =
                categoryService.getAllCategories(team.getTeamId(), FALSE, TEST_USER_NAME);

        // then
        assertThat(categoryGetResList.getCategories().get(0).getName()).isEqualTo(category.getName());
        assertThat(categoryGetResList.getTotal()).isEqualTo(1);
    }

    @Test
    @DisplayName("삭제된 카테고리 복구 테스트")
    void category_복구() {
        // given
        CategoryRestoreReq req =
                CategoryRestoreReq.builder()
                        .categoryId(category.getCategoryId())
                        .username(TEST_USER_NAME)
                        .build();

        Category restoredCategory =
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .sequence(category.getSequence())
                        .isDeleted(TRUE)
                        .team(category.getTeam())
                        .build();

        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(categoryRepository.findByCategoryId(any())).thenReturn(restoredCategory);
        when(categoryRepository.save(any())).thenReturn(category);

        // when
        categoryService.restoreCategory(req);

        // then
        verify(categoryRepository).save(argumentCaptor.capture());
        assertEquals(FALSE, argumentCaptor.getValue().getIsDeleted());
    }

    @Test
    @DisplayName("카테고리 이름 변경 테스트")
    void category_이름_변경() throws Exception {
        // given
        CategoryEditReq req =
                CategoryEditReq.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_ANOTHER_CATEGORY_NAME)
                        .build();

        Category renamedCategory =
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(req.getName())
                        .sequence(category.getSequence())
                        .isDeleted(FALSE)
                        .team(category.getTeam())
                        .build();

        given(userRepository.findByUsername(any())).willReturn(TEST_USER);
        given(categoryRepository.findByCategoryId(anyLong())).willReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(renamedCategory);

        // when
        categoryService.editCategory(req);

        // then
        verify(categoryRepository).save(argumentCaptor.capture());
        assertEquals(TEST_ANOTHER_CATEGORY_NAME, argumentCaptor.getValue().getName());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void category_삭제() throws Exception {
        // given
        CategoryDeleteReq req = CategoryDeleteReq.builder().categoryId(TEST_CATEGORY_ID).build();
        req.setUsername(TEST_USER_NAME);
        given(userRepository.findByUsername(any())).willReturn(TEST_USER);
        given(categoryRepository.findByCategoryId(anyLong())).willReturn(category);

        // when
        categoryService.deleteCategory(req);

        // then
        verify(categoryRepository).save(argumentCaptor.capture());
        assertEquals(TRUE, argumentCaptor.getValue().getIsDeleted());
    }
}
