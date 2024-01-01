package com.vt.valuetogether.domain.category.service.impl;

import static java.lang.Boolean.FALSE;

import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryRestoreReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryDeleteRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryEditRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
import com.vt.valuetogether.domain.category.dto.response.CategoryRestoreRes;
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
        checkTeamMember(team, user);

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
        Category category = getCategoryById(req.getCategoryId());

        checkTeamMember(category.getTeam(), user);

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(req.getName())
                        .sequence(category.getSequence())
                        .team(category.getTeam())
                        .isDeleted(false)
                        .build());

        return new CategoryEditRes();
    }

    @Override
    @Transactional
    public CategoryDeleteRes deleteCategory(CategoryDeleteReq req) {
        User user = getUserByUsername(req.getUsername());
        Category category = getCategoryById(req.getCategoryId());

        checkTeamMember(category.getTeam(), user);

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .sequence(0.0)
                        .team(category.getTeam())
                        .isDeleted(true)
                        .build());

        return new CategoryDeleteRes();
    }

    @Override
    @Transactional
    public CategoryChangeSequenceRes changeCategorySequence(
            CategoryChangeSequenceReq categoryChangeSequenceReq) {
        User user = getUserByUsername(categoryChangeSequenceReq.getUsername());
        Category category = getCategoryById(categoryChangeSequenceReq.getCategoryId());

        checkTeamMember(category.getTeam(), user);

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
    public CategoryGetResList getAllCategories(Long teamId, boolean isDeleted, String username) {
        User user = getUserByUsername(username);
        Team team = teamRepository.findByTeamId(teamId);
        checkTeamMember(team, user);
        List<CategoryGetRes> categoryGetReses =
                CategoryServiceMapper.INSTANCE.toCategoryGetResList(
                        categoryRepository.findByTeamTeamIdAndIsDeletedOrderBySequenceAsc(teamId, isDeleted));
        return CategoryGetResList.builder()
                .categories(categoryGetReses)
                .total(categoryGetReses.size())
                .build();
    }

    @Override
    @Transactional
    public CategoryRestoreRes restoreCategory(CategoryRestoreReq req) {
        User user = getUserByUsername(req.getUsername());
        Category category = getCategoryById(req.getCategoryId());

        checkTeamMember(category.getTeam(), user);

        categoryRepository.save(
                Category.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .sequence(category.getSequence())
                        .team(category.getTeam())
                        .isDeleted(false)
                        .build());

        return new CategoryRestoreRes();
    }

    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private Category getCategoryById(Long categoryId){
        Category category = categoryRepository.findByCategoryId(categoryId);
        CategoryValidator.isSoftDeleted(category);
        return category;
    }

    private Double getMaxSequence(Long teamId) {
        return categoryRepository.getMaxSequence(teamId);
    }

    private Double getAverageSequence(Double preSequence, Double postSequence) {
        return (preSequence + postSequence) / 2;
    }

    private void checkTeamMember(Team team, User user){
        TeamValidator.validate(team);
        TeamRoleValidator.validate(team.getTeamRoleList());
        TeamRoleValidator.checkIsTeamMember(team.getTeamRoleList(), user);
    }
}
