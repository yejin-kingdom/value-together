package com.vt.valuetogether.domain.team.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    private String teamName;
    private String teamDescription;

    @OneToMany(mappedBy = "team")
    private List<TeamRole> teamRoleList;

    @Builder
    private Team(Long teamId,
        String teamName,
        String teamDescription) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }

    public void addTeamRole(TeamRole teamRole) {
        this.teamRoleList.add(teamRole);
    }
}
