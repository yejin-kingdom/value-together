package com.vt.valuetogether.domain.comment.entity;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.team.entity.TeamRole;
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
@Table(name = "tb_comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "teamRoleId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TeamRole teamRole;

    @Builder
    private Comment(Long commentId, String content, Card card, TeamRole teamRole) {
        this.commentId = commentId;
        this.content = content;
        this.card = card;
        this.teamRole = teamRole;
    }
}
