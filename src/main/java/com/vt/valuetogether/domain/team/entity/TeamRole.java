package com.vt.valuetogether.domain.team.entity;

import com.vt.valuetogether.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRoleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId")
    private Team team;

    private Role role;

    @Builder
    private TeamRole(User user, Team team, Role role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }
}
