package com.vt.valuetogether.domain.category.service;

import com.vt.valuetogether.domain.category.dto.request.CategoryDeleteReq;
import com.vt.valuetogether.domain.category.dto.request.CategoryEditReq;
import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategoryDeleteRes;
import com.vt.valuetogether.domain.category.dto.response.CategoryEditRes;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;

public interface CategoryService {
    CategorySaveRes saveCategory(CategorySaveReq categorySaveReq);

    CategoryEditRes editCategory(CategoryEditReq req);

    CategoryDeleteRes deleteCategory(CategoryDeleteReq req);
}
