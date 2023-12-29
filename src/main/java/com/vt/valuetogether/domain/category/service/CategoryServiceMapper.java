package com.vt.valuetogether.domain.category.service;

import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryServiceMapper {
    CategoryServiceMapper INSTANCE = Mappers.getMapper(CategoryServiceMapper.class);

    CategorySaveRes toCategorySaveRes(Category category);
}
