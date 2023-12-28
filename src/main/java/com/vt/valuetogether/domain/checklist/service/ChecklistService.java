package com.vt.valuetogether.domain.checklist.service;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistDeleteReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistDeleteRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistUpdateRes;

public interface ChecklistService {
    ChecklistSaveRes saveChecklist(ChecklistSaveReq checklistSaveReq);

    ChecklistUpdateRes updateChecklist(ChecklistUpdateReq checklistUpdateReq);

    ChecklistDeleteRes deleteChecklist(ChecklistDeleteReq checklistDeleteReq);
}
