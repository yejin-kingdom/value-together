package com.vt.valuetogether.domain.card.service;

import com.vt.valuetogether.domain.card.dto.response.CardSaveRes;
import com.vt.valuetogether.domain.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardServiceMapper {
    CardServiceMapper INSTANCE = Mappers.getMapper(CardServiceMapper.class);

    CardSaveRes toCardSavaRes(Card card);
}
