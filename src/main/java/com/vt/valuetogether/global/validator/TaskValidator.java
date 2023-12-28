package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NULL_CONTENT;

import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.global.exception.GlobalException;

public class TaskValidator {
    public static void validate(TaskSaveReq taskSaveReq) {
        if (isNullContent(taskSaveReq.getContent())) {
            throw new GlobalException(NULL_CONTENT);
        }
    }

    private static boolean isNullContent(String content) {
        return content == null;
    }
}
