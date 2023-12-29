package com.vt.valuetogether.domain.card.service.impl;

import static com.vt.valuetogether.infra.s3.S3Util.FilePath.CARD;

import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardDeleteRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.domain.card.service.CardServiceMapper;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.infra.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final S3Util s3Util;
    private final Double NEXT_SEQUENCE = 1.0;

    @Override
    @Transactional
    public CardSaveRes saveCard(CardSaveReq cardSaveReq, MultipartFile multipartFile) {
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
                                .categoryId(cardSaveReq.getCategoryId())
                                .build()));
    }

    private Double getMaxSequence(Long categoryId) {
        return cardRepository.getMaxSequence(categoryId) + NEXT_SEQUENCE;
    }

    @Override
    @Transactional
    public CardUpdateRes updateCard(CardUpdateReq cardUpdateReq, MultipartFile multipartFile) {
        Card prevCard = cardRepository.findByCardId(cardUpdateReq.getCardId());
        CardValidator.validate(prevCard);
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
                        .categoryId(prevCard.getCategoryId())
                        .build());
        return new CardUpdateRes();
    }

    @Override
    @Transactional
    public CardDeleteRes deleteCard(CardDeleteReq cardDeleteReq) {
        Card card = cardRepository.findByCardId(cardDeleteReq.getCardId());
        CardValidator.validate(card);
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
}
