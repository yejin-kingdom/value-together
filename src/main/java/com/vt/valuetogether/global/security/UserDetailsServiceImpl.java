package com.vt.valuetogether.global.security;

import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserValidator.checkExistingUsername(user);

        return new UserDetailsImpl(user);
    }
}
