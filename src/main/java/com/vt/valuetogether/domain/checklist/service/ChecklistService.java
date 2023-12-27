package com.vt.valuetogether.domain.checklist.service;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final ChecklistRepository checklistRepository;

    public ChecklistSaveRes saveChecklist(ChecklistSaveReq checklistSaveReq) {
        // TODO ADD Card
        return ChecklistServiceMapper.INSTANCE.toChecklistSaveRes(
                checklistRepository.save(Checklist.builder().title(checklistSaveReq.getTitle()).build()));
    }

    @Mapper
    public interface ChecklistServiceMapper {
        ChecklistServiceMapper INSTANCE = Mappers.getMapper(ChecklistServiceMapper.class);

        ChecklistSaveRes toChecklistSaveRes(Checklist checklist);
    }
}
