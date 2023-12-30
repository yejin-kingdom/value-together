package com.vt.valuetogether.domain.worker.service;

import com.vt.valuetogether.domain.worker.dto.response.WorkerAddRes;
import com.vt.valuetogether.domain.worker.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkerServiceMapper {

    WorkerServiceMapper INSTANCE = Mappers.getMapper(WorkerServiceMapper.class);

    WorkerAddRes toWorkerAddRes(Worker worker);
}
