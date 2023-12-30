package com.vt.valuetogether.domain.card.service.impl;

import static com.vt.valuetogether.infra.s3.S3Util.FilePath.CARD;

import com.vt.valuetogether.domain.card.dto.request.CardChangeSequenceReq;
import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardChangeSequenceRes;
import com.vt.valuetogether.domain.card.dto.response.CardDeleteRes;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.domain.card.service.CardServiceMapper;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.global.validator.CategoryValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import com.vt.valuetogether.infra.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final S3Util s3Util;

    @Override
    @Transactional
    public CardSaveRes saveCard(CardSaveReq cardSaveReq, MultipartFile multipartFile) {
        User user = getUserByUsername(cardSaveReq.getUsername());
        Category category = categoryRepository.findByCategoryId(cardSaveReq.getCategoryId());
        CategoryValidator.validate(category);
        TeamRoleValidator.checkIsTeamMember(category.getTeam().getTeamRoleList(), user);

        Double sequence = getMaxSequence(cardSaveReq.getCategoryId());
        String fileUrl = s3Util.uploadFile(multipartFile, CARD);
        return CardServiceMapper.INSTANCE.toCardSavaRes(
                cardRepository.save(
                        Card.builder()
                                .name(cardSaveReq.getName())
                                .description(cardSaveReq.getDescription())
                                .fileUrl(fileUrl)
                                .sequence(sequence)
                                .deadline(cardSaveReq.getDeadline())
                                .category(category)
                                .build()));
    }

    private Double getMaxSequence(Long categoryId) {
        return cardRepository.getMaxSequence(categoryId);
    }

    @Override
    @Transactional
    public CardUpdateRes updateCard(CardUpdateReq cardUpdateReq, MultipartFile multipartFile) {
        User user = getUserByUsername(cardUpdateReq.getUsername());
        Card prevCard = getCardByCardId(cardUpdateReq.getCardId());
        TeamRoleValidator.checkIsTeamMember(prevCard.getCategory().getTeam().getTeamRoleList(), user);

        deleteFile(prevCard.getFileUrl());
        String fileUrl = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            fileUrl = s3Util.uploadFile(multipartFile, CARD);
        }
        cardRepository.save(
                Card.builder()
                        .cardId(prevCard.getCardId())
                        .name(cardUpdateReq.getName())
                        .description(cardUpdateReq.getDescription())
                        .fileUrl(fileUrl)
                        .sequence(prevCard.getSequence())
                        .deadline(cardUpdateReq.getDeadline())
                        .category(prevCard.getCategory())
                        .build());
        return new CardUpdateRes();
    }

    @Override
    @Transactional
    public CardDeleteRes deleteCard(CardDeleteReq cardDeleteReq) {
        User user = getUserByUsername(cardDeleteReq.getUsername());
        Card card = getCardByCardId(cardDeleteReq.getCardId());
        TeamRoleValidator.checkIsTeamMember(card.getCategory().getTeam().getTeamRoleList(), user);

        deleteFile(card.getFileUrl());
        cardRepository.delete(card);
        return new CardDeleteRes();
    }

    private void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }
        s3Util.deleteFile(fileUrl, CARD);
    }

    @Override
    @Transactional(readOnly = true)
    public CardGetRes getCard(Long cardId, String username) {
        User user = getUserByUsername(username);
        Card card = getCardByCardId(cardId);
        TeamRoleValidator.checkIsTeamMember(card.getCategory().getTeam().getTeamRoleList(), user);
        return CardServiceMapper.INSTANCE.toCardGetRes(card);
    }

    @Override
    @Transactional
    public CardChangeSequenceRes changeSequence(CardChangeSequenceReq cardChangeSequenceReq) {
        User user = getUserByUsername(cardChangeSequenceReq.getUsername());
        Card card = getCardByCardId(cardChangeSequenceReq.getCardId());
        Category category = categoryRepository.findByCategoryId(cardChangeSequenceReq.getCategoryId());
        CategoryValidator.validate(category);
        TeamRoleValidator.checkIsTeamMember(category.getTeam().getTeamRoleList(), user);

        Double averageSequence =
                getAverageSequence(
                        cardChangeSequenceReq.getPreSequence(), cardChangeSequenceReq.getPostSequence());

        cardRepository.save(
                Card.builder()
                        .cardId(card.getCardId())
                        .name(card.getName())
                        .description(card.getDescription())
                        .fileUrl(card.getFileUrl())
                        .sequence(averageSequence)
                        .deadline(card.getDeadline())
                        .category(category)
                        .build());

        return new CardChangeSequenceRes();
    }

    private Double getAverageSequence(Double preSequence, Double postSequence) {
        return (preSequence + postSequence) / 2;
    }

    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private Card getCardByCardId(Long cardId) {
        Card card = cardRepository.findByCardId(cardId);
        CardValidator.validate(card);
        return card;
    }
}
