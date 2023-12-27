package com.vt.valuetogether.domain.user.repository;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = EmailAuth.class, idClass = String.class)
public interface EmailAuthRepository {
    EmailAuth findById(String email);

    EmailAuth deleteById(String email);

    boolean existsById(String email);

    EmailAuth save(EmailAuth emailAuth);
}
