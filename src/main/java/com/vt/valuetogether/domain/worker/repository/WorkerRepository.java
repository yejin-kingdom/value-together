package com.vt.valuetogether.domain.worker.repository;

import com.vt.valuetogether.domain.worker.entity.Worker;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Worker.class, idClass = Long.class)
public interface WorkerRepository {

    Worker save(Worker worker);

    Worker findByWorkerId(Long workerId);

    void delete(Worker worker);
}
