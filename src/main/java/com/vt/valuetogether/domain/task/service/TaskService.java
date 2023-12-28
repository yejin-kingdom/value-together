package com.vt.valuetogether.domain.task.service;

import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;

public interface TaskService {
    TaskSaveRes saveTask(TaskSaveReq taskSaveReq);
}
