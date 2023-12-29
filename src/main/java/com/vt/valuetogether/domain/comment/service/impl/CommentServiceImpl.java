package com.vt.valuetogether.domain.comment.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.comment.repository.CommentRepository;
import com.vt.valuetogether.domain.comment.service.CommentService;
import com.vt.valuetogether.domain.comment.service.CommentServiceMapper;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.service.UserService;
import com.vt.valuetogether.global.validator.CardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq req) {
        User user = userService.getUser(req.getUsername());
        Card card = cardRepository.findByCardId(req.getCardId());
        CardValidator.validate(card);

        // 작성자가 해당 팀에 속해있는지 확인 로직 추가

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder().content(req.getContent()).card(card).user(user).build()));
    }
}
