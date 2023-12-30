package com.vt.valuetogether.domain.checklist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.team.repository.TeamRepository;
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
    @Autowired private CardRepository cardRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TeamRepository teamRepository;

    @Test
    @DisplayName("checklist 저장 테스트")
    void checklist_저장() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);

        // when
        Checklist checklist = checklistRepository.save(TEST_CHECKLIST);

        // then
        assertThat(checklist.getTitle()).isEqualTo(TEST_TITLE);
    }

    @Test
    @DisplayName("id로 checklist 조회 테스트")
    void id_checklist_조회() {
        // given
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);
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
        teamRepository.save(TEST_TEAM);
        categoryRepository.save(TEST_CATEGORY);
        cardRepository.save(TEST_CARD);
        Checklist saveChecklist = checklistRepository.save(TEST_CHECKLIST);

        // when
        checklistRepository.delete(TEST_CHECKLIST);
        Checklist checklist = checklistRepository.findByChecklistId(saveChecklist.getChecklistId());

        // then
        assertThat(checklist).isEqualTo(null);
    }
}
