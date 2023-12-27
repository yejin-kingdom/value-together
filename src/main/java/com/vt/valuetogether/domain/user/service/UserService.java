package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateIntroduceReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdatePasswordReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateIntroduceRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdatePasswordRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateUsernameRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;

public interface UserService {
    UserVerifyEmailRes sendEmail(UserVerifyEmailReq req);

    UserConfirmEmailRes confirmEmail(String email, String code);

    UserSignupRes signup(UserSignupReq req);

    UserUpdateUsernameRes updateUsername(UserUpdateUsernameReq req);

    UserUpdateIntroduceRes updateIntroduce(UserUpdateIntroduceReq req);

    UserUpdatePasswordRes updatePassword(UserUpdatePasswordReq req);
}
