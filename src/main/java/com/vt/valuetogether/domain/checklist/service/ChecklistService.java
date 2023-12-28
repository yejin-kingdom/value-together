package com.vt.valuetogether.domain.checklist.service;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistDeleteReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistDeleteRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistUpdateRes;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.global.validator.ChecklistValidator;
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

    public ChecklistUpdateRes updateChecklist(ChecklistUpdateReq checklistUpdateReq) {
        Checklist prevChecklist =
                checklistRepository.findByChecklistId(checklistUpdateReq.getChecklistId());
        ChecklistValidator.validate(prevChecklist);

        // TODO ADD Card
        checklistRepository.save(
                Checklist.builder()
                        .checklistId(checklistUpdateReq.getChecklistId())
                        .title(checklistUpdateReq.getTitle())
                        .build());
        return new ChecklistUpdateRes();
    }

    public ChecklistDeleteRes deleteChecklist(ChecklistDeleteReq checklistDeleteReq) {
        Checklist checklist =
                checklistRepository.findByChecklistId(checklistDeleteReq.getChecklistId());
        ChecklistValidator.validate(checklist);
        checklistRepository.delete(checklist);
        return new ChecklistDeleteRes();
    }

    @Mapper
    public interface ChecklistServiceMapper {
        ChecklistServiceMapper INSTANCE = Mappers.getMapper(ChecklistServiceMapper.class);

        ChecklistSaveRes toChecklistSaveRes(Checklist checklist);
    }
}
