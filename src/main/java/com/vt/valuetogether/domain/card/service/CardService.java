package com.vt.valuetogether.domain.card.service;

import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import org.springframework.web.multipart.MultipartFile;

public interface CardService {
    CardSaveRes saveCard(CardSaveReq cardSaveReq, MultipartFile multipartFile);
}
