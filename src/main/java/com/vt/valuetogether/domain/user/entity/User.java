package com.vt.valuetogether.domain.user.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String email;

    private String introduce;

    private String profileImageUrl;

    private String oauthId;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Builder
    private User(
            Long userId,
            String username,
            String password,
            String email,
            String introduce,
            String profileImageUrl,
            String oauthId,
            Role role,
            Provider provider) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.oauthId = oauthId;
        this.role = role;
        this.provider = provider;
    }
}
