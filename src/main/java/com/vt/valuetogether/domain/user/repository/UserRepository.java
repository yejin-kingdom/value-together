package com.vt.valuetogether.domain.user.repository;

import com.vt.valuetogether.domain.user.entity.User;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

}
