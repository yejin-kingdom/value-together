package com.vt.valuetogether.domain.oauth.service;

import static com.vt.valuetogether.domain.oauth.constant.OAuth2Constant.DEFAULT_NAME;

import com.vt.valuetogether.domain.oauth.OAuth2Attributes;
import com.vt.valuetogether.domain.oauth.dto.request.OAuth2LoginReq;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.infra.s3.S3Util;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${default.image.address}")
    private String defaultProfileImageUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        String userNameAttributeName =
                userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2LoginReq oAuth2LoginReq = OAuth2Attributes.extract(providerType, attributes);

        User saveUser = userRepository.findByOauthId(oAuth2LoginReq.getOauthId());

        if (saveUser == null) {
            saveUser = save(oAuth2LoginReq);
        }

        Map<String, Object> customAttribute =
                customAttribute(attributes, userNameAttributeName, oAuth2LoginReq, saveUser.getUsername());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(saveUser.getRole().getValue())),
                customAttribute,
                userNameAttributeName);
    }

    private String makeRandomName() {
        String randomName;
        do {
            randomName = generateRandomName();
        } while (userRepository.existsByUsername(randomName));
        return randomName;
    }

    private String generateRandomName() {
        return DEFAULT_NAME + UUID.randomUUID().toString().substring(0, 6);
    }

    private User save(OAuth2LoginReq oAuth2LoginReq) {
        User user =
                User.builder()
                        .username(makeRandomName())
                        .email(oAuth2LoginReq.getEmail())
                        .profileImageUrl(defaultProfileImageUrl)
                        .oauthId(oAuth2LoginReq.getOauthId())
                        .provider(oAuth2LoginReq.getProvider())
                        .role(Role.USER)
                        .build();

        return userRepository.save(user);
    }

    private Map<String, Object> customAttribute(
            Map<String, Object> attributes,
            String userNameAttributeName,
            OAuth2LoginReq oauthUserProfile,
            String username) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("username", username);
        customAttribute.put("provider", oauthUserProfile.getProvider());
        customAttribute.put("email", oauthUserProfile.getEmail());
        customAttribute.put("oAuthId", oauthUserProfile.getOauthId());
        return customAttribute;
    }
}
