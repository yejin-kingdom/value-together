package com.vt.valuetogether.domain.oauth.dto;

import com.vt.valuetogether.domain.user.entity.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserProfile {

    private String oauthId;
    private String email;
    private String name;
    private String imageUrl;
    private Provider provider;

    @Builder
    private OAuth2UserProfile(
            String oauthId, String email, String name, String imageUrl, Provider provider) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }
}
