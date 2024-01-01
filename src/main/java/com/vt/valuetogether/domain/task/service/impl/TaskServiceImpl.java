package com.vt.valuetogether.domain.task.service.impl;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.task.dto.request.TaskDeleteReq;
import com.vt.valuetogether.domain.task.dto.request.TaskSaveReq;
import com.vt.valuetogether.domain.task.dto.request.TaskUpdateReq;
import com.vt.valuetogether.domain.task.dto.response.TaskDeleteRes;
import com.vt.valuetogether.domain.task.dto.response.TaskSaveRes;
import com.vt.valuetogether.domain.task.dto.response.TaskUpdateRes;
import com.vt.valuetogether.domain.task.entity.Task;
import com.vt.valuetogether.domain.task.repository.TaskRepository;
import com.vt.valuetogether.domain.task.service.TaskService;
import com.vt.valuetogether.domain.task.service.TaskServiceMapper;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.ChecklistValidator;
import com.vt.valuetogether.global.validator.TaskValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final ChecklistRepository checklistRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskSaveRes saveTask(TaskSaveReq taskSaveReq) {
        TaskValidator.validate(taskSaveReq);
        User user = getUserByUsername(taskSaveReq.getUsername());
        Checklist checklist = checklistRepository.findByChecklistId(taskSaveReq.getChecklistId());
        ChecklistValidator.validate(checklist);
        TeamRoleValidator.checkIsTeamMember(
                checklist.getCard().getCategory().getTeam().getTeamRoleList(), user);
        return TaskServiceMapper.INSTANCE.toTaskSaveRes(
                taskRepository.save(
                        Task.builder()
                                .content(taskSaveReq.getContent())
                                .isCompleted(Boolean.FALSE)
                                .checklist(checklist)
                                .build()));
    }

    @Override
    @Transactional
    public TaskUpdateRes updateTask(TaskUpdateReq taskUpdateReq) {
        TaskValidator.validate(taskUpdateReq);
        User user = getUserByUsername(taskUpdateReq.getUsername());
        Task prevTask = getTaskById(taskUpdateReq.getTaskId());
        TeamRoleValidator.checkIsTeamMember(
                prevTask.getChecklist().getCard().getCategory().getTeam().getTeamRoleList(), user);
        taskRepository.save(
                Task.builder()
                        .taskId(prevTask.getTaskId())
                        .content(taskUpdateReq.getContent())
                        .isCompleted(taskUpdateReq.getIsCompleted())
                        .checklist(prevTask.getChecklist())
                        .build());
        return new TaskUpdateRes();
    }

    @Override
    @Transactional
    public TaskDeleteRes deleteTask(TaskDeleteReq taskDeleteReq) {
        User user = getUserByUsername(taskDeleteReq.getUsername());
        Task task = getTaskById(taskDeleteReq.getTaskId());
        TeamRoleValidator.checkIsTeamMember(
                task.getChecklist().getCard().getCategory().getTeam().getTeamRoleList(), user);
        taskRepository.delete(task);
        return new TaskDeleteRes();
    }

    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private Task getTaskById(Long taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        TaskValidator.validate(task);
        return task;
    }
}
