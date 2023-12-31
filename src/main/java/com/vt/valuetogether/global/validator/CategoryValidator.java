package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_DELETED_CATEGORY;
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

    public static void isSoftDeleted(Category category) {
        if (checkIsNull(category)) {
            throw new GlobalException(NOT_FOUND_CATEGORY);
        }
        if (!checkIsDeleted(category)) { // 삭제되지 않은 카테고리를 복구하려는 경우
            throw new GlobalException(NOT_DELETED_CATEGORY);
        }
    }

    private static boolean checkIsNull(Category category) {
        return category == null;
    }

    private static boolean checkIsDeleted(Category category) {
        return category.getIsDeleted();
    }
}
