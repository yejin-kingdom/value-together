package com.vt.valuetogether.domain.worker.service;

import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.dto.response.WorkerAddRes;
import com.vt.valuetogether.domain.worker.dto.response.WorkerDeleteRes;

public interface WorkerService {

    WorkerAddRes addWorker(WorkerAddReq req);

    WorkerDeleteRes deleteWorker(WorkerDeleteReq req);
}
