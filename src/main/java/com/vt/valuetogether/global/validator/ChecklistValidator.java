package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_CHECKLIST;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.global.exception.GlobalException;

public class ChecklistValidator {
    public static void validate(Checklist checklist) {
        if (isNullChecklist(checklist)) {
            throw new GlobalException(NOT_FOUND_CHECKLIST);
        }
    }

    private static boolean isNullChecklist(Checklist checklist) {
        return checklist == null;
    }
}
