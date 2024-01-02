package com.vt.valuetogether.domain.user.repository;

import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.domain.user.entity.User;
import java.util.List;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {
    User findByUsername(String username);

    User save(User user);

    User findByOauthId(String oauthId);

    Boolean existsByUsername(String username);

    User findByUserId(Long userId);

    List<User> findAllByUsernameIn(List<String> username);

    User findByEmailAndProvider(String email, Provider provider);
}
