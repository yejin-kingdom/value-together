package com.vt.valuetogether.domain.team.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_team")
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(unique = true)
    private String teamName;

    private String teamDescription;
    private String backgroundColor;
    private Boolean isDeleted;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamRole> teamRoleList = new ArrayList<>();

    @Builder
    private Team(
            Long teamId,
            String teamName,
            String teamDescription,
            String backgroundColor,
            Boolean isDeleted,
            List<TeamRole> teamRoleList) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.backgroundColor = backgroundColor;
        this.isDeleted = isDeleted;
        this.teamRoleList = teamRoleList;
    }
}
