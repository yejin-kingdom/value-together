package com.vt.valuetogether.domain.worker.repository;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.domain.worker.entity.WorkerPK;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Worker.class, idClass = WorkerPK.class)
public interface WorkerRepository {

    Worker save(Worker worker);

    Worker findByTeamRoleAndCard(TeamRole teamRole, Card card);

    void delete(Worker worker);
}
