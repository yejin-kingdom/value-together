package com.vt.valuetogether.domain.task.service;

import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.dto.response.TaskUpdateRes;

public interface TaskService {
    TaskSaveRes saveTask(TaskSaveReq taskSaveReq);

    TaskUpdateRes updateTask(TaskUpdateReq taskUpdateReq);
}
