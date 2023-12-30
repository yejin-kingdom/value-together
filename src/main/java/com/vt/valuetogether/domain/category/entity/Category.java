package com.vt.valuetogether.domain.category.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.team.entity.Team;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private Double sequence;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @Builder
    private Category(Long categoryId, String name, Double sequence, Boolean isDeleted, Team team) {
        this.categoryId = categoryId;
        this.name = name;
        this.sequence = sequence;
        this.isDeleted = isDeleted;
        this.team = team;
    }
}
