package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_TASK;
import static com.vt.valuetogether.global.meta.ResultCode.NULL_CONTENT;

import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.global.exception.GlobalException;

public class TaskValidator {
    public static void validate(TaskSaveReq taskSaveReq) {
        if (isNullContent(taskSaveReq.getContent())) {
            throw new GlobalException(NULL_CONTENT);
        }
    }

    public static void validate(TaskUpdateReq taskUpdateReq) {
        if (isNullContent(taskUpdateReq.getContent())) {
            throw new GlobalException(NULL_CONTENT);
        }
    }

    public static void validate(Task task) {
        if (isNullTask(task)) {
            throw new GlobalException(NOT_FOUND_TASK);
        }
    }

    private static boolean isNullTask(Task task) {
        return task == null;
    }

    private static boolean isNullContent(String content) {
        return content == null;
    }
}
