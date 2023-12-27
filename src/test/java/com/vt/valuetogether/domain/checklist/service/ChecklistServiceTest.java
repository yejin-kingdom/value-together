package com.vt.valuetogether.domain.checklist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
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
        ChecklistSaveRes checklistSaveRes = checklistService.saveChecklist(checklistSaveReq);

        // then
        assertThat(checklistSaveRes.getChecklistId()).isEqualTo(TEST_CHECKLIST_ID);
        verify(checklistRepository).save(any());
    }
}
