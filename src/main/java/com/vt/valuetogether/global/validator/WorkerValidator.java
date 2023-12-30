package com.vt.valuetogether.global.validator;

import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;

public class WorkerValidator {

    public static void validator(Worker worker) {
        if (isWorkerNull(worker)) {
            throw new GlobalException(ResultCode.NOT_FOUND_WORKER);
        }
    }

    private static boolean isWorkerNull(Worker worker) {
        return worker == null;
    }
}
