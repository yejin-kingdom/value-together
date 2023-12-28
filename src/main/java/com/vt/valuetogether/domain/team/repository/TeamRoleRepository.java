package com.vt.valuetogether.domain.team.repository;

import com.vt.valuetogether.domain.team.entity.TeamRole;
import java.util.List;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = TeamRole.class, idClass = Long.class)
public interface TeamRoleRepository {

    TeamRole save(TeamRole teamRole);

    List<TeamRole> saveAll(Iterable<TeamRole> teamRoleList);

    List<TeamRole> findByTeam_TeamId(Long teamId);
}
