package com.vt.valuetogether.domain.task.repository;

import com.vt.valuetogether.domain.task.entity.Task;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Task.class, idClass = Long.class)
public interface TaskRepository {
    Task save(Task task);
}
