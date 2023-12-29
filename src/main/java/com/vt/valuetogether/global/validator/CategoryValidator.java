package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_CATEGORY;

import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.global.exception.GlobalException;

public class CategoryValidator {
    public static void validate(Category category) {
        // 카테고리가 존재하지 않거나 삭제된 경우 NOT_FOUND_CATEGORY Exception 발생
        if (checkIsNull(category) || checkIsDeleted(category)) {
            throw new GlobalException(NOT_FOUND_CATEGORY);
        }
    }

    private static boolean checkIsNull(Category category) {
        return category == null;
    }

    private static boolean checkIsDeleted(Category category) {
        return category.getIsDeleted();
    }
}
