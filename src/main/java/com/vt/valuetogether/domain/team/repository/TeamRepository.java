package com.vt.valuetogether.domain.team.repository;

import com.vt.valuetogether.domain.team.entity.Team;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Team.class, idClass = Long.class)
public interface TeamRepository {

    Team save(Team team);
}
