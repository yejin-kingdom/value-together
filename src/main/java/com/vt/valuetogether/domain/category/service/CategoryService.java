package com.vt.valuetogether.domain.category.service;

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

public interface CategoryService {
    CategorySaveRes saveCategory(CategorySaveReq categorySaveReq);

    CategoryEditRes editCategory(CategoryEditReq req);

    CategoryDeleteRes deleteCategory(CategoryDeleteReq req);

    CategoryChangeSequenceRes changeCategorySequence(
            CategoryChangeSequenceReq categoryChangeSequenceReq);

    CategoryGetResList getAllCategories(Long teamId, boolean isDeleted, String username);

    CategoryRestoreRes restoreCategory(CategoryRestoreReq req);
}
