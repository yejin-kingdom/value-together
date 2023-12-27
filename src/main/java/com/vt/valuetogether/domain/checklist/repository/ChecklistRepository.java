package com.vt.valuetogether.domain.checklist.repository;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Checklist.class, idClass = Long.class)
public interface ChecklistRepository {
    Checklist save(Checklist checklist);
}
