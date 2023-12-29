package com.vt.valuetogether.domain.category.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.test.CategoryTest;
import com.vt.valuetogether.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest implements CategoryTest, UserTest {
    @InjectMocks private CategoryServiceImpl categoryService;

    @Mock private CategoryRepository categoryRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private UserRepository userRepository;
    @Mock private TeamRoleRepository teamRoleRepository;

    @Test
    @DisplayName("category 저장 테스트")
    void category_저장() {
        // given
        Long teamId = 1L;
        String name = "category";
        CategorySaveReq categorySaveReq = CategorySaveReq.builder().teamId(teamId).name(name).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(teamRepository.findByTeamId(any())).thenReturn(TEST_TEAM);
        //        when(categoryRepository.getMaxSequence(any())).thenReturn(TEST_CATEGORY_SEQUENCE);
        //        when(categoryRepository.save(any())).thenReturn(TEST_CATEGORY);

        // when

        // then
        assertThatThrownBy(() -> categoryService.saveCategory(categorySaveReq))
                .isInstanceOf(GlobalException.class);
        verify(userRepository).findByUsername(any());
        verify(teamRepository).findByTeamId(any());
        //        verify(categoryRepository).getMaxSequence(any());
        //        verify(categoryRepository).save(any());
    }
}
