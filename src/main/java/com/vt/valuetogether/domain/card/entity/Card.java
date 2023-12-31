package com.vt.valuetogether.domain.card.entity;

import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.worker.entity.Worker;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String name;
    private String description;
    private String fileUrl;
    private Double sequence;
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    // TODO ADD Comments
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Checklist> checklists;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Worker> workers;

    @Builder
    private Card(
            Long cardId,
            String name,
            String description,
            String fileUrl,
            Double sequence,
            LocalDateTime deadline,
            Category category) {
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.fileUrl = fileUrl;
        this.sequence = sequence;
        this.deadline = deadline;
        this.category = category;
    }
}
