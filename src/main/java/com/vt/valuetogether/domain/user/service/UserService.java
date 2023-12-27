package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;

public interface UserService {
    UserVerifyEmailRes sendEmail(UserVerifyEmailReq req);

    UserConfirmEmailRes confirmEmail(String email, String code);

    UserSignupRes signup(UserSignupReq req);
}
