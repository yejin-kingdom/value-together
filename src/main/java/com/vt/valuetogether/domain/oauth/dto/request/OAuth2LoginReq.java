package com.vt.valuetogether.domain.oauth.dto.request;

import com.vt.valuetogether.domain.user.entity.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2LoginReq {

    private String username;
    private String oauthId;
    private String email;
    private String imageUrl;
    private Provider provider;

    @Builder
    private OAuth2LoginReq(
            String username, String oauthId, String email, String imageUrl, Provider provider) {
        this.username = username;
        this.oauthId = oauthId;
        this.email = email;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }
}
