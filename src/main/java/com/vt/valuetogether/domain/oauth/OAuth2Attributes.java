package com.vt.valuetogether.domain.oauth;

import com.vt.valuetogether.domain.oauth.dto.OAuth2UserProfile;
import com.vt.valuetogether.domain.oauth.exception.OAuth2ProviderInvalidException;
import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.global.meta.ResultCode;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Attributes {
    GITHUB("GITHUB") {
        @Override
        public OAuth2UserProfile of(Map<String, Object> attributes) {
            return OAuth2UserProfile.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("login"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .provider(Provider.GITHUB)
                    .build();
        }
    },
    NAVER("NAVER") {
        @Override
        @SuppressWarnings("unchecked")
        public OAuth2UserProfile of(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return OAuth2UserProfile.builder()
                    .email((String) response.get("email"))
                    .name((String) response.get("nickname"))
                    .imageUrl((String) response.get("profile_image"))
                    .provider(Provider.NAVER)
                    .build();
        }
    },
    GOOGLE("GOOGLE") {
        @Override
        public OAuth2UserProfile of(Map<String, Object> attributes) {
            return OAuth2UserProfile.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("name"))
                    .imageUrl((String) attributes.get("picture"))
                    .provider(Provider.GOOGLE)
                    .build();
        }
    };

    private final String providerName;

    public static OAuth2UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values()) // 해당 enum 의 요소들을 순서대로 순회
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(() -> new OAuth2ProviderInvalidException(ResultCode.INVALID_OAUTH_PROVIDER))
                .of(attributes);
    }

    public abstract OAuth2UserProfile of(Map<String, Object> attributes); // 추상 메서드로 구현
}
