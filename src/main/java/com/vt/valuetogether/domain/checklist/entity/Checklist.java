package com.vt.valuetogether.domain.checklist.entity;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.task.entity.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "cardId")
    private Card card;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Builder
    private Checklist(Long checklistId, String title, Card card) {
        this.checklistId = checklistId;
        this.title = title;
        this.card = card;
    }
}
