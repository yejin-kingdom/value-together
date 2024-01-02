package com.vt.valuetogether.domain.comment.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.comment.dto.request.CommentDeleteReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentUpdateReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentDeleteRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentUpdateRes;
import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.domain.comment.repository.CommentRepository;
import com.vt.valuetogether.domain.comment.service.CommentService;
import com.vt.valuetogether.domain.comment.service.CommentServiceMapper;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.global.validator.CommentValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final TeamRoleRepository teamRoleRepository;

    @Override
    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq req) {
        Card card = findCard(req.getCardId());
        TeamRole teamRole = findTeamRole(req.getUsername(), card.getCategory().getTeam().getTeamId());

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder().content(req.getContent()).card(card).teamRole(teamRole).build()));
    }

    @Override
    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq req) {
        Comment comment = findComment(req.getCommentId());
        CommentValidator.checkCommentUser(
                comment.getTeamRole().getUser().getUsername(), req.getUsername());

        commentRepository.save(
                Comment.builder()
                        .commentId(req.getCommentId())
                        .content(req.getContent())
                        .card(comment.getCard())
                        .teamRole(comment.getTeamRole())
                        .build());

        return new CommentUpdateRes();
    }

    @Override
    @Transactional
    public CommentDeleteRes deleteComment(CommentDeleteReq req) {
        Comment comment = findComment(req.getCommentId());
        CommentValidator.checkCommentUser(
                comment.getTeamRole().getUser().getUsername(), req.getUsername());
        commentRepository.delete(comment);
        return new CommentDeleteRes();
    }

    private Card findCard(Long cardId) {
        Card card = cardRepository.findByCardId(cardId);
        CardValidator.validate(card);
        return card;
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        CommentValidator.validate(comment);
        return comment;
    }

    private TeamRole findTeamRole(String username, Long teamId) {
        TeamRole teamRole = teamRoleRepository.findByUserUsernameAndTeamTeamId(username, teamId);
        TeamRoleValidator.validate(teamRole);
        return teamRole;
    }
}
