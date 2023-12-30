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
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq req) {
        User user = findUser(req.getUsername());
        Card card = findCard(req.getCardId());

        TeamRoleValidator.checkIsTeamMember(card.getCategory().getTeam().getTeamRoleList(), user);

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder().content(req.getContent()).card(card).user(user).build()));
    }

    private User findUser(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private Card findCard(Long cardId) {
        Card card = cardRepository.findByCardId(cardId);
        CardValidator.validate(card);
        return card;
    }
}
