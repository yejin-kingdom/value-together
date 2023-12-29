package com.vt.valuetogether.domain.worker.controller;

import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.dto.response.WorkerAddRes;
import com.vt.valuetogether.domain.worker.dto.response.WorkerDeleteRes;
import com.vt.valuetogether.domain.worker.service.WorkerService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workers")
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping("")
    public RestResponse<WorkerAddRes> addWorker(@RequestBody WorkerAddReq req) {
        return RestResponse.success(workerService.addWorker(req));
    }

    @DeleteMapping("")
    public RestResponse<WorkerDeleteRes> deleteWorker(@RequestBody WorkerDeleteReq req) {
        return RestResponse.success(workerService.deleteWorker(req));
    }
}
