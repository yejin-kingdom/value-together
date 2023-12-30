package com.vt.valuetogether.domain.category.service;

import com.vt.valuetogether.domain.card.dto.response.CardInnerCategoryRes;
import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.category.dto.response.CategoryGetRes;
import com.vt.valuetogether.domain.category.dto.response.CategorySaveRes;
import com.vt.valuetogether.domain.category.entity.Category;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryServiceMapper {
    CategoryServiceMapper INSTANCE = Mappers.getMapper(CategoryServiceMapper.class);

    @Mapping(source = "deadline", target = "deadline")
    default String toStringDateTime(LocalDateTime deadline) {
        if (deadline == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = deadline.atZone(ZoneId.of("Asia/Seoul"));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Mapping(source = "deadline", target = "deadline")
    CardInnerCategoryRes toCardGetRes(Card card);

    List<CardInnerCategoryRes> toCardGetReses(List<Card> cards);

    List<CategoryGetRes> toCategoryGetResList(List<Category> categories);

    CategorySaveRes toCategorySaveRes(Category category);
}
