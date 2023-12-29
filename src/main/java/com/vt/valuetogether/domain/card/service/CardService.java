package com.vt.valuetogether.domain.card.service;

import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardDeleteRes;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import org.springframework.web.multipart.MultipartFile;

public interface CardService {
    CardSaveRes saveCard(CardSaveReq cardSaveReq, MultipartFile multipartFile);

    CardUpdateRes updateCard(CardUpdateReq cardUpdateReq, MultipartFile multipartFile);

    CardDeleteRes deleteCard(CardDeleteReq cardDeleteReq);

    CardGetRes getCard(Long cardId);
}
