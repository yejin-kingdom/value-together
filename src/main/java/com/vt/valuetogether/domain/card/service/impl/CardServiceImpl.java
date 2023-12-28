package com.vt.valuetogether.domain.card.service.impl;

import static com.vt.valuetogether.infra.s3.S3Util.FilePath.CARD;

import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.domain.card.service.CardServiceMapper;
import com.vt.valuetogether.infra.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final S3Util s3Util;

    @Override
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
        Double sequence = cardRepository.getMaxSequence(categoryId);
        if (sequence == null) return 1.0;
        return sequence + 1.0;
    }
}
