package com.vt.valuetogether.domain.checklist.service;

import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChecklistServiceMapper {
    ChecklistServiceMapper INSTANCE = Mappers.getMapper(ChecklistServiceMapper.class);

    ChecklistSaveRes toChecklistSaveRes(Checklist checklist);
}
