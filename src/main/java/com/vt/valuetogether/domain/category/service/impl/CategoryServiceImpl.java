package com.vt.valuetogether.domain.category.service.impl;

import static java.lang.Boolean.FALSE;

import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.category.service.CategoryService;
import com.vt.valuetogether.domain.category.service.CategoryServiceMapper;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.TeamValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CategorySaveRes saveCategory(CategorySaveReq categorySaveReq) {
        User user = userRepository.findByUsername(categorySaveReq.getUsername());
        UserValidator.validate(user);
        Team team = teamRepository.findByTeamId(categorySaveReq.getTeamId());
        TeamValidator.validate(team);
        TeamRoleValidator.checkIsTeamMember(team.getTeamRoleList(), user);

        return CategoryServiceMapper.INSTANCE.toCategorySaveRes(
                categoryRepository.save(
                        Category.builder()
                                .name(categorySaveReq.getName())
                                .sequence(getMaxSequence(team.getTeamId()))
                                .isDeleted(FALSE)
                                .team(team)
                                .build()));
    }

    private Double getMaxSequence(Long teamId) {
        return categoryRepository.getMaxSequence(teamId);
    }
}
