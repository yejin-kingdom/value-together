package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateProfileReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyPasswordReq;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateProfileRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyPasswordRes;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserVerifyEmailRes sendEmail(UserVerifyEmailReq req);

    UserConfirmEmailRes confirmEmail(String email, String code);

    UserSignupRes signup(UserSignupReq req);

    UserVerifyPasswordRes verifyPassword(UserVerifyPasswordReq req);

    UserUpdateProfileRes updateProfile(UserUpdateProfileReq req, MultipartFile multipartFile);
}
