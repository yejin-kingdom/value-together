package com.vt.valuetogether.domain.category.service.impl;

import static java.lang.Boolean.FALSE;

import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryDeleteRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryEditRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.category.service.CategoryService;
import com.vt.valuetogether.domain.category.service.CategoryServiceMapper;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.CategoryValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.TeamValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import java.util.List;
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
        User user = getUserByUsername(categorySaveReq.getUsername());
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

    @Override
    @Transactional
    public CategoryEditRes editCategory(CategoryEditReq req) {
        User user = getUserByUsername(req.getUsername());
        Category category = categoryRepository.findByCategoryId(req.getCategoryId());
        CategoryValidator.validate(category);

        Team team = category.getTeam();
        TeamValidator.validate(team);

        TeamRoleValidator.validate(team.getTeamRoleList());
        TeamRoleValidator.checkIsTeamMember(team.getTeamRoleList(), user);

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(req.getName())
                        .sequence(category.getSequence())
                        .team(team)
                        .isDeleted(false)
                        .build());

        return new CategoryEditRes();
    }

    @Override
    @Transactional
    public CategoryDeleteRes deleteCategory(CategoryDeleteReq req) {
        User user = getUserByUsername(req.getUsername());
        Category category = categoryRepository.findByCategoryId(req.getCategoryId());
        CategoryValidator.validate(category);

        Team team = category.getTeam();
        TeamValidator.validate(team);

        TeamRoleValidator.validate(team.getTeamRoleList());
        TeamRoleValidator.checkIsTeamMember(team.getTeamRoleList(), user);

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .sequence(0.0)
                        .team(team)
                        .isDeleted(true)
                        .build());

        return new CategoryDeleteRes();
    }

    @Override
    @Transactional
    public CategoryChangeSequenceRes changeCategorySequence(
            CategoryChangeSequenceReq categoryChangeSequenceReq) {
        User user = getUserByUsername(categoryChangeSequenceReq.getUsername());
        Category category =
                categoryRepository.findByCategoryId(categoryChangeSequenceReq.getCategoryId());
        CategoryValidator.validate(category);
        TeamRoleValidator.checkIsTeamMember(category.getTeam().getTeamRoleList(), user);

        Double sequence =
                getAverageSequence(
                        categoryChangeSequenceReq.getPreSequence(),
                        categoryChangeSequenceReq.getPostSequence());

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .sequence(sequence)
                        .isDeleted(category.getIsDeleted())
                        .team(category.getTeam())
                        .build());

        return new CategoryChangeSequenceRes();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryGetResList getAllCategories(Long teamId, String username) {
        User user = getUserByUsername(username);
        Team team = teamRepository.findByTeamId(teamId);
        TeamValidator.validate(team);
        TeamRoleValidator.checkIsTeamMember(team.getTeamRoleList(), user);
        List<CategoryGetRes> categoryGetReses =
                CategoryServiceMapper.INSTANCE.toCategoryGetResList(
                        categoryRepository.findByTeamTeamIdOrderBySequenceAsc(teamId));
        return CategoryGetResList.builder()
                .categories(categoryGetReses)
                .total(categoryGetReses.size())
                .build();
    }

    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private Double getMaxSequence(Long teamId) {
        return categoryRepository.getMaxSequence(teamId);
    }

    private Double getAverageSequence(Double preSequence, Double postSequence) {
        return (preSequence + postSequence) / 2;
    }
}
