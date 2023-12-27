package com.vt.valuetogether.domain.team.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_team_role")
public class TeamRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRoleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    private TeamRole(User user, Team team, Role role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }
}
