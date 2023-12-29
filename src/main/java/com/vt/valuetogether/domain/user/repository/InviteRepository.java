package com.vt.valuetogether.domain.user.repository;

import com.vt.valuetogether.domain.user.entity.InviteCode;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = InviteCode.class, idClass = String.class)
public interface InviteRepository {
    InviteCode findById(String code);

    InviteCode save(InviteCode inviteCode);
}
