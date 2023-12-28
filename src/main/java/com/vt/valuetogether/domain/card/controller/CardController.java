package com.vt.valuetogether.domain.card.controller;

import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.global.response.RestResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
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
            @RequestPart CardSaveReq cardSaveReq,
            @RequestPart(required = false) MultipartFile multipartFile) {
        return RestResponse.success(cardService.saveCard(cardSaveReq, multipartFile));
    }

    @PatchMapping
    public RestResponse<CardUpdateRes> updateCard(
            @RequestPart CardUpdateReq cardUpdateReq,
            @RequestPart(required = false) MultipartFile multipartFile)
            throws IOException {
        return RestResponse.success(cardService.updateCard(cardUpdateReq, multipartFile));
    }
}
