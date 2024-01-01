package com.vt.valuetogether.domain.card.controller;

import com.vt.valuetogether.domain.card.dto.request.CardChangeSequenceReq;
import com.vt.valuetogether.domain.card.dto.request.CardDeleteReq;
import com.vt.valuetogether.domain.card.dto.request.CardSaveReq;
import com.vt.valuetogether.domain.card.dto.request.CardUpdateReq;
import com.vt.valuetogether.domain.card.dto.response.CardChangeSequenceRes;
import com.vt.valuetogether.domain.card.dto.response.CardDeleteRes;
import com.vt.valuetogether.domain.card.dto.response.CardGetRes;
import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.dto.response.CardUpdateRes;
import com.vt.valuetogether.domain.card.service.CardService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{cardId}")
    public RestResponse<CardGetRes> getCard(
            @PathVariable Long cardId, @AuthenticationPrincipal UserDetails userDetails) {
        return RestResponse.success(cardService.getCard(cardId, userDetails.getUsername()));
    }

    @PostMapping
    public RestResponse<CardSaveRes> saveCard(
            @RequestPart CardSaveReq cardSaveReq,
            @RequestPart(required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails) {
        cardSaveReq.setUsername(userDetails.getUsername());
        return RestResponse.success(cardService.saveCard(cardSaveReq, multipartFile));
    }

    @PatchMapping
    public RestResponse<CardUpdateRes> updateCard(
            @RequestPart CardUpdateReq cardUpdateReq,
            @RequestPart(required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails) {
        cardUpdateReq.setUsername(userDetails.getUsername());
        return RestResponse.success(cardService.updateCard(cardUpdateReq, multipartFile));
    }

    @PatchMapping("/order")
    public RestResponse<CardChangeSequenceRes> changeSequence(
            @RequestBody CardChangeSequenceReq cardChangeSequenceReq,
            @AuthenticationPrincipal UserDetails userDetails) {
        cardChangeSequenceReq.setUsername(userDetails.getUsername());
        return RestResponse.success(cardService.changeSequence(cardChangeSequenceReq));
    }

    @DeleteMapping
    public RestResponse<CardDeleteRes> deleteCard(
            @RequestBody CardDeleteReq cardDeleteReq, @AuthenticationPrincipal UserDetails userDetails) {
        cardDeleteReq.setUsername(userDetails.getUsername());
        return RestResponse.success(cardService.deleteCard(cardDeleteReq));
    }
}
