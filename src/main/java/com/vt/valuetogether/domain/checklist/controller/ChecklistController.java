package com.vt.valuetogether.domain.checklist.controller;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistUpdateRes;
import com.vt.valuetogether.domain.checklist.service.ChecklistService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checklists")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @PostMapping
    public RestResponse<ChecklistSaveRes> saveChecklist(
            @RequestBody ChecklistSaveReq checklistSaveReq) {
        return RestResponse.success(checklistService.saveChecklist(checklistSaveReq));
    }

    @PatchMapping
    public RestResponse<ChecklistUpdateRes> updateChecklist(
            @RequestBody ChecklistUpdateReq checklistUpdateReq) {
        return RestResponse.success(checklistService.updateChecklist(checklistUpdateReq));
    }
}
