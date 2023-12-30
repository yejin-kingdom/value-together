package com.vt.valuetogether.domain.worker.repository;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.domain.worker.entity.WorkerPK;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Worker.class, idClass = WorkerPK.class)
public interface WorkerRepository {

    Worker save(Worker worker);

    Worker findByUserAndCard(User user, Card card);

    void delete(Worker worker);
}
