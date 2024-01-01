package com.vt.valuetogether.domain.task.controller;

import com.vt.valuetogether.domain.task.dto.request.TaskDeleteReq;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.dto.response.TaskDeleteRes;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.dto.response.TaskUpdateRes;
import com.vt.valuetogether.domain.task.service.TaskService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public RestResponse<TaskSaveRes> saveTask(
            @RequestBody TaskSaveReq taskSaveReq, @AuthenticationPrincipal UserDetails userDetails) {
        taskSaveReq.setUsername(userDetails.getUsername());
        return RestResponse.success(taskService.saveTask(taskSaveReq));
    }

    @PatchMapping
    public RestResponse<TaskUpdateRes> updateTask(
            @RequestBody TaskUpdateReq taskUpdateReq, @AuthenticationPrincipal UserDetails userDetails) {
        taskUpdateReq.setUsername(userDetails.getUsername());
        return RestResponse.success(taskService.updateTask(taskUpdateReq));
    }

    @DeleteMapping
    public RestResponse<TaskDeleteRes> deleteTask(@RequestBody TaskDeleteReq taskDeleteReq) {
        return RestResponse.success(taskService.deleteTask(taskDeleteReq));
    }
}
