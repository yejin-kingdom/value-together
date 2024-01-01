package com.vt.valuetogether.domain.card.service;

import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.comment.dto.response.CommentGetRes;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.worker.dto.response.WorkerGetRes;
import com.vt.valuetogether.domain.worker.entity.Worker;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardServiceMapper {
    CardServiceMapper INSTANCE = Mappers.getMapper(CardServiceMapper.class);

    @Mapping(source = "deadline", target = "deadline")
    default String toStringDateTime(LocalDateTime deadline) {
        if (deadline == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = deadline.atZone(ZoneId.of("Asia/Seoul"));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Mapping(source = "user.username", target = "username")
    CommentGetRes toCommentGetRes(Comment comment);

    @Mapping(source = "teamRole.user.profileImageUrl", target = "profileImageUrl")
    @Mapping(source = "teamRole.user.username", target = "username")
    WorkerGetRes toWorkerGetRes(Worker worker);

    @Mapping(source = "deadline", target = "deadline")
    CardGetRes toCardGetRes(Card card);

    CardSaveRes toCardSavaRes(Card card);
}
