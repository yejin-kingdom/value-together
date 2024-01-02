package com.vt.valuetogether.domain.category.controller;

import com.vt.valuetogether.domain.category.dto.request.CategoryChangeSequenceReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryRestoreReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryChangeSequenceRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryDeleteRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryEditRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetResList;
import com.vt.valuetogether.domain.category.dto.response.CategoryRestoreRes;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.service.CategoryService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/teams/{teamId}/categories")
    public RestResponse<CategoryGetResList> getAllCategories(
            @PathVariable Long teamId,
            @RequestParam("isDeleted") boolean isDeleted,
            @AuthenticationPrincipal UserDetails userDetails) {
        return RestResponse.success(
                categoryService.getAllCategories(teamId, isDeleted, userDetails.getUsername()));
    }

    @PostMapping("/categories")
    public RestResponse<CategorySaveRes> saveCategory(
            @RequestBody CategorySaveReq categorySaveReq,
            @AuthenticationPrincipal UserDetails userDetails) {
        categorySaveReq.setUsername(userDetails.getUsername());
        return RestResponse.success(categoryService.saveCategory(categorySaveReq));
    }

    @PatchMapping("/categories")
    public RestResponse<CategoryEditRes> editCategory(
            @RequestBody CategoryEditReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(categoryService.editCategory(req));
    }

    @PatchMapping("/categories/order")
    public RestResponse<CategoryChangeSequenceRes> changeCategorySequence(
            @RequestBody CategoryChangeSequenceReq categoryChangeSequenceReq,
            @AuthenticationPrincipal UserDetails userDetails) {
        categoryChangeSequenceReq.setUsername(userDetails.getUsername());
        return RestResponse.success(categoryService.changeCategorySequence(categoryChangeSequenceReq));
    }

    @PatchMapping("/categories/restore")
    public RestResponse<CategoryRestoreRes> restoreCategory(
            @RequestBody CategoryRestoreReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(categoryService.restoreCategory(req));
    }

    @DeleteMapping("/categories")
    public RestResponse<CategoryDeleteRes> deleteCategory(
            @RequestBody CategoryDeleteReq req, @AuthenticationPrincipal UserDetails userDetails) {
        req.setUsername(userDetails.getUsername());
        return RestResponse.success(categoryService.deleteCategory(req));
    }
}
