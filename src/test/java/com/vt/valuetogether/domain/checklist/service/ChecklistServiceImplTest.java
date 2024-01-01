package com.vt.valuetogether.domain.checklist.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistDeleteReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.checklist.service.impl.ChecklistServiceImpl;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.test.ChecklistTest;
import com.vt.valuetogether.test.TeamRoleTest;
import com.vt.valuetogether.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChecklistServiceImplTest implements ChecklistTest, UserTest, TeamRoleTest {
    @InjectMocks private ChecklistServiceImpl checklistService;

    @Mock private ChecklistRepository checklistRepository;
    @Mock private CardRepository cardRepository;
    @Mock private UserRepository userRepository;
    private Category category;
    private Team team;
    private Card card;
    private Checklist checklist;

    @BeforeEach
    void setUp() {
        team =
                Team.builder()
                        .teamId(TEST_TEAM_ID)
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .isDeleted(TEST_TEAM_IS_DELETED)
                        .teamRoleList(List.of(TEST_TEAM_ROLE))
                        .build();
        category =
                Category.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .name(TEST_CATEGORY_NAME)
                        .sequence(TEST_CATEGORY_SEQUENCE)
                        .isDeleted(TEST_CATEGORY_IS_DELETED)
                        .team(team)
                        .build();
        card =
                Card.builder()
                        .cardId(TEST_CARD_ID)
                        .name(TEST_NAME)
                        .description(TEST_DESCRIPTION)
                        .fileUrl(TEST_FILE_URL)
                        .sequence(TEST_SEQUENCE)
                        .deadline(TEST_DEADLINE)
                        .category(category)
                        .build();
        checklist =
                Checklist.builder().checklistId(TEST_CHECKLIST_ID).title(TEST_TITLE).card(card).build();
    }

    @Test
    @DisplayName("checklist 저장 테스트")
    void checklist_저장() {
        // given
        String title = "title";
        ChecklistSaveReq checklistSaveReq = ChecklistSaveReq.builder().title(title).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(cardRepository.findByCardId(any())).thenReturn(card);
        when(checklistRepository.save(any())).thenReturn(checklist);

        // when
        checklistService.saveChecklist(checklistSaveReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(cardRepository).findByCardId(any());
        verify(checklistRepository).save(any());
    }

    @Test
    @DisplayName("checklist 수정 테스트")
    void checklist_수정() {
        // given
        Long checklistId = 1L;
        String title = "updatedTitle";
        ChecklistUpdateReq checklistUpdateReq =
                ChecklistUpdateReq.builder().checklistId(checklistId).title(title).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(checklistRepository.findByChecklistId(any())).thenReturn(checklist);
        when(checklistRepository.save(any())).thenReturn(TEST_UPDATED_CHECKLIST);

        // when
        checklistService.updateChecklist(checklistUpdateReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(checklistRepository).findByChecklistId(any());
        verify(checklistRepository).save(any());
    }

    @Test
    @DisplayName("checklist 삭제 테스트")
    void checklist_삭제() {
        // given
        Long checklistId = 1L;
        ChecklistDeleteReq checklistDeleteReq =
                ChecklistDeleteReq.builder().checklistId(checklistId).build();
        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        when(checklistRepository.findByChecklistId(any())).thenReturn(checklist);

        // when
        checklistService.deleteChecklist(checklistDeleteReq);

        // then
        verify(userRepository).findByUsername(any());
        verify(checklistRepository).findByChecklistId(any());
        verify(checklistRepository).delete(any());
    }
}
