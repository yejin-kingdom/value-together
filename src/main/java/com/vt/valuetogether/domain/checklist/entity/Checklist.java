package com.vt.valuetogether.domain.checklist.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_checklist")
public class Checklist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    private String title;

    // TODO ADD Card

    @Builder
    private Checklist(Long checklistId, String title) {
        this.checklistId = checklistId;
        this.title = title;
    }
}
