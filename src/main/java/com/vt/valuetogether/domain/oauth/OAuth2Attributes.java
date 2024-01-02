package com.vt.valuetogether.domain.oauth;

import com.vt.valuetogether.domain.oauth.dto.request.OAuth2LoginReq;
import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.meta.ResultCode;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Attributes {
    GITHUB("GITHUB") { // image = avatar_url
        @Override
        public OAuth2LoginReq of(Map<String, Object> attributes) {
            return OAuth2LoginReq.builder()
                    .oauthId(attributes.get("id").toString())
                    .email((String) attributes.get("email"))
                    .provider(Provider.GITHUB)
                    .build();
        }
    },
    NAVER("NAVER") { // image = profile_image
        @Override
        @SuppressWarnings("unchecked")
        public OAuth2LoginReq of(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return OAuth2LoginReq.builder()
                    .oauthId((String) response.get("id"))
                    .email((String) response.get("email"))
                    .provider(Provider.NAVER)
                    .build();
        }
    },
    GOOGLE("GOOGLE") { // image = picture
        @Override
        public OAuth2LoginReq of(Map<String, Object> attributes) {
            return OAuth2LoginReq.builder()
                    .oauthId((String) attributes.get("sub"))
                    .email((String) attributes.get("email"))
                    .provider(Provider.GOOGLE)
                    .build();
        }
    };

    private final String providerName;

    public static OAuth2LoginReq extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findAny()
                .orElseThrow(() -> new GlobalException(ResultCode.INVALID_OAUTH_PROVIDER))
                .of(attributes);
    }

    public abstract OAuth2LoginReq of(Map<String, Object> attributes);
}
