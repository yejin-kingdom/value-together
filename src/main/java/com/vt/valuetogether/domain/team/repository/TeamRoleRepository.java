package com.vt.valuetogether.domain.team.repository;

import com.vt.valuetogether.domain.team.entity.TeamRole;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = TeamRole.class, idClass = Long.class)
public interface TeamRoleRepository {

    TeamRole save(TeamRole teamRole);
}
