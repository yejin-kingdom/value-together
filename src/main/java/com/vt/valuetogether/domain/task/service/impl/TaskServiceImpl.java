package com.vt.valuetogether.domain.task.service.impl;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.domain.task.repository.TaskRepository;
import com.vt.valuetogether.domain.task.service.TaskService;
import com.vt.valuetogether.domain.task.service.TaskServiceMapper;
import com.vt.valuetogether.global.validator.ChecklistValidator;
import com.vt.valuetogether.global.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final ChecklistRepository checklistRepository;
    private final TaskRepository taskRepository;

    @Override
    public TaskSaveRes saveTask(TaskSaveReq taskSaveReq) {
        TaskValidator.validate(taskSaveReq);
        Checklist checklist = checklistRepository.findByChecklistId(taskSaveReq.getChecklistId());
        ChecklistValidator.validate(checklist);
        return TaskServiceMapper.INSTANCE.toTaskSaveRes(
                taskRepository.save(
                        Task.builder()
                                .content(taskSaveReq.getContent())
                                .isCompleted(Boolean.FALSE)
                                .checklist(checklist)
                                .build()));
    }
}
