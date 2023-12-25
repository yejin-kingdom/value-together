package com.vt.valuetogether.domain.user.repository;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {}
