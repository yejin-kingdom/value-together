package com.vt.valuetogether.domain.team.entity;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "tb_team_role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "teamId"})})
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRoleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "teamId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isDeleted;

    @Builder
    private TeamRole(Long teamRoleId, User user, Team team, Role role, boolean isDeleted) {
        this.teamRoleId = teamRoleId;
        this.user = user;
        this.team = team;
        this.role = role;
        this.isDeleted = isDeleted;
    }
}
