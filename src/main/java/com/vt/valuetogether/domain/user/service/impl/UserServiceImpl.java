package com.vt.valuetogether.domain.user.service.impl;

import static com.vt.valuetogether.domain.user.entity.Provider.LOCAL;
import static com.vt.valuetogether.domain.user.entity.Role.USER;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.user.service.UserService;
import com.vt.valuetogether.domain.user.service.UserServiceMapper;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserSignupRes signup(UserSignupReq req) {
        UserValidator.validate(req);

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.checkDuplicatedUsername(user);

        // req.getEmail()이 인증된 이메일인지 확인 필요

        User saveUser =
                userRepository.save(
                        User.builder()
                                .username(req.getUsername())
                                .password(passwordEncoder.encode(req.getPassword()))
                                .email(req.getEmail())
                                .role(USER)
                                .provider(LOCAL)
                                .build());

        return UserServiceMapper.INSTANCE.toUserSignupRes(saveUser);
    }
}
