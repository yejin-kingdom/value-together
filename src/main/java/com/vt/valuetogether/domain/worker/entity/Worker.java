package com.vt.valuetogether.domain.worker.entity;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@IdClass(WorkerPK.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_worker")
public class Worker {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId")
    private Card card;

    @Builder
    private Worker(User user, Card card) {
        this.user = user;
        this.card = card;
    }
}