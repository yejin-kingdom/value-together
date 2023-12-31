package com.vt.valuetogether.domain.task.entity;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Column;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_task")
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "checklistId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Checklist checklist;

    @Builder
    private Task(Long taskId, String content, Boolean isCompleted, Checklist checklist) {
        this.taskId = taskId;
        this.content = content;
        this.isCompleted = isCompleted;
        this.checklist = checklist;
    }
}
