package com.vt.valuetogether.domain.oauth.exception;

import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;

public class OAuth2ProviderInvalidException extends GlobalException {

    public OAuth2ProviderInvalidException(ResultCode resultCode) {
        super(resultCode);
    }
}
