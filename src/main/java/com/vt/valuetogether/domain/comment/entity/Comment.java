package com.vt.valuetogether.domain.comment.entity;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.model.BaseEntity;
import com.vt.valuetogether.domain.user.entity.User;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    private Comment(Long commentId, String content, Card card, User user) {
        this.commentId = commentId;
        this.content = content;
        this.card = card;
        this.user = user;
    }
}
