package com.vt.valuetogether.domain.team.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    private String teamName;
    private String teamDescription;

    @OneToMany(mappedBy = "team")
    private List<TeamRole> teamRoleList = new ArrayList<>();
}
