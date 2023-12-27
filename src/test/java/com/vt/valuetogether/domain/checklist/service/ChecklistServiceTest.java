package com.vt.valuetogether.domain.checklist.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistDeleteReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.test.ChecklistTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChecklistServiceTest implements ChecklistTest {
    @InjectMocks private ChecklistService checklistService;

    @Mock private ChecklistRepository checklistRepository;

    @Test
    @DisplayName("checklist 저장 테스트")
    void checklist_저장() {
        // given
        String title = "title";
        ChecklistSaveReq checklistSaveReq = ChecklistSaveReq.builder().title(title).build();
        when(checklistRepository.save(any())).thenReturn(TEST_CHECKLIST);

        // when
        checklistService.saveChecklist(checklistSaveReq);

        // then
        verify(checklistRepository).save(any());
    }

    @Test
    @DisplayName("checklist 수정 테스트")
    void checklist_수정() {
        // given
        Long checklistId = 1L;
        String title = "title";
        ChecklistUpdateReq checklistUpdateReq =
                ChecklistUpdateReq.builder().checklistId(checklistId).title(title).build();
        when(checklistRepository.findByChecklistId(any())).thenReturn(TEST_CHECKLIST);
        // when
        checklistService.updateChecklist(checklistUpdateReq);

        // then
        verify(checklistRepository).findByChecklistId(any());
    }

    @Test
    @DisplayName("checklist 삭제 테스트")
    void checklist_삭제() {
        // given
        Long checklistId = 1L;
        ChecklistDeleteReq checklistDeleteReq =
                ChecklistDeleteReq.builder().checklistId(checklistId).build();
        when(checklistRepository.findByChecklistId(any())).thenReturn(TEST_CHECKLIST);

        // when
        checklistService.deleteChecklist(checklistDeleteReq);

        // then
        verify(checklistRepository).delete(any());
    }
}
