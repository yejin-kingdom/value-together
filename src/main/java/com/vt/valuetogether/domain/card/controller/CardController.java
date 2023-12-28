package com.vt.valuetogether.domain.card.controller;

import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public RestResponse<CardSaveRes> saveCard(
            @RequestPart CardSaveReq cardSaveReq, @RequestPart MultipartFile multipartFile) {
        return RestResponse.success(cardService.saveCard(cardSaveReq, multipartFile));
    }
}
