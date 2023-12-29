package com.vt.valuetogether.domain.category.service;

import com.vt.valuetogether.domain.category.dto.request.CategorySaveReq;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;

public interface CategoryService {
    CategorySaveRes saveCategory(CategorySaveReq categorySaveReq);
}
