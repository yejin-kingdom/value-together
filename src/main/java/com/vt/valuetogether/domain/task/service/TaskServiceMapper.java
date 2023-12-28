package com.vt.valuetogether.domain.task.service;

import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskServiceMapper {
    TaskServiceMapper INSTANCE = Mappers.getMapper(TaskServiceMapper.class);

    TaskSaveRes toTaskSaveRes(Task task);
}
