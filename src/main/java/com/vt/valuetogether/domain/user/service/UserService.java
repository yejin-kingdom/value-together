package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;

public interface UserService {
    UserSignupRes signup(UserSignupReq req);
}
