package com.vt.valuetogether.domain.oauth.dto.request;

import com.vt.valuetogether.domain.user.entity.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2LoginReq {

    private String oauthId;
    private String email;
    private Provider provider;

    @Builder
    private OAuth2LoginReq(String oauthId, String email, Provider provider) {
        this.oauthId = oauthId;
        this.email = email;
        this.provider = provider;
    }
}
