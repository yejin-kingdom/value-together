package com.vt.valuetogether.domain.checklist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.test.ChecklistTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChecklistRepositoryTest implements ChecklistTest {
    @Autowired private ChecklistRepository checklistRepository;

    @Test
    @DisplayName("checklist 저장 테스트")
    void checklist_저장() {
        // given

        // when
        Checklist checklist = checklistRepository.save(TEST_CHECKLIST);

        // then
        assertThat(checklist.getTitle()).isEqualTo(TEST_TITLE);
    }

    @Test
    @DisplayName("id로 checklist 조회 테스트")
    void id_checklist_조회() {
        // given
        Checklist saveChecklist = checklistRepository.save(TEST_CHECKLIST);

        // when
        Checklist checklist = checklistRepository.findByChecklistId(saveChecklist.getChecklistId());

        // then
        assertThat(checklist.getTitle()).isEqualTo(saveChecklist.getTitle());
    }

    @Test
    @DisplayName("checklist 삭제 테스트")
    void checklist_삭제() {
        // given
        Checklist saveChecklist = checklistRepository.save(TEST_CHECKLIST);

        // when
        checklistRepository.delete(TEST_CHECKLIST);
        Checklist checklist = checklistRepository.findByChecklistId(saveChecklist.getChecklistId());

        // then
        assertThat(checklist).isEqualTo(null);
    }
}
