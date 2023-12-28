package com.vt.valuetogether.domain.task.controller;

import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.dto.response.TaskUpdateRes;
import com.vt.valuetogether.domain.task.service.TaskService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public RestResponse<TaskSaveRes> saveTask(@RequestBody TaskSaveReq taskSaveReq) {
        return RestResponse.success(taskService.saveTask(taskSaveReq));
    }

    @PatchMapping
    public RestResponse<TaskUpdateRes> updateTask(@RequestBody TaskUpdateReq taskUpdateReq) {
        return RestResponse.success(taskService.updateTask(taskUpdateReq));
    }
}
