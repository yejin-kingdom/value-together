package com.vt.valuetogether.domain.oauth.service;

import com.vt.valuetogether.domain.oauth.OAuth2Attributes;
import com.vt.valuetogether.domain.oauth.dto.OAuth2UserProfile;
import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // Oauth 서비스에서 가져온 유저 정보를 담고 있음

        String providerType =
                userRequest
                        .getClientRegistration() // ex) naver, google, github
                        .getRegistrationId()
                        .toUpperCase();

        String userNameAttributeName =
                userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();
        // google = sub 가 고유값, naver = id 가 고유값

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserProfile oauthUserProfile = OAuth2Attributes.extract(providerType, attributes);

        User saveUser = userRepository.findByEmail(oauthUserProfile.getEmail());

        if (saveUser == null || saveUser.getProvider() == Provider.LOCAL) {
            save(oauthUserProfile);
        }

        Map<String, Object> customAttribute =
                customAttribute(attributes, userNameAttributeName, oauthUserProfile);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getValue())),
                customAttribute,
                userNameAttributeName);
    }

    private void save(OAuth2UserProfile oauthUserProfile) {
        //        String password = passwordEncoder.encode("oauth2Password"); // TODO: constant
        User user =
                User.builder()
                        .username(oauthUserProfile.getName())
                        .email(oauthUserProfile.getEmail())
                        .password("abcD1234@")
                        .profileImageUrl(oauthUserProfile.getImageUrl())
                        .provider(oauthUserProfile.getProvider())
                        .role(Role.USER)
                        .build();

        userRepository.save(user);
    }

    private Map<String, Object> customAttribute(
            Map<String, Object> attributes,
            String userNameAttributeName,
            OAuth2UserProfile oauthUserProfile) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", oauthUserProfile.getProvider());
        customAttribute.put("name", oauthUserProfile.getName());
        customAttribute.put("email", oauthUserProfile.getEmail());
        return customAttribute;
    }
}
