package com.vt.valuetogether.domain.user.controller;

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
import com.vt.valuetogether.domain.user.service.UserService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/email")
    public RestResponse<UserVerifyEmailRes> sendEmail(@RequestBody UserVerifyEmailReq req) {
        return RestResponse.success(userService.sendEmail(req));
    }

    @GetMapping("/confirm-email")
    public RestResponse<UserConfirmEmailRes> confirmEmail(
            @RequestParam(name = "email") String email, @RequestParam(name = "authCode") String code) {
        return RestResponse.success(userService.confirmEmail(email, code));
    }

    @PostMapping("/signup")
    public RestResponse<UserSignupRes> signup(@RequestBody UserSignupReq req) {
        return RestResponse.success(userService.signup(req));
    }

    // 프로필 수정
    @PatchMapping("/name")
    public RestResponse<UserUpdateUsernameRes> updateUsername(
        @RequestBody UserUpdateUsernameReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.updateUsername(req));
    }

    @PatchMapping("/introduce")
    public RestResponse<UserUpdateIntroduceRes> updateIntroduce(
        @RequestBody UserUpdateIntroduceReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.updateIntroduce(req));
    }

    @PatchMapping("/password")
    public RestResponse<UserUpdatePasswordRes> updateIntroduce(
        @RequestBody UserUpdatePasswordReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.updatePassword(req));
    }
}
