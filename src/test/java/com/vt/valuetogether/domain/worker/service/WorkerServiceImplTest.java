package com.vt.valuetogether.domain.worker.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.team.entity.Team;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.domain.worker.repository.WorkerRepository;
import com.vt.valuetogether.domain.worker.service.impl.WorkerServiceImpl;
import com.vt.valuetogether.test.WorkerTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest implements WorkerTest {

    @Mock UserRepository userRepository;
    @Mock CardRepository cardRepository;
    @Mock WorkerRepository workerRepository;
    @Mock TeamRoleRepository teamRoleRepository;
    @InjectMocks private WorkerServiceImpl workerService;
    private Card card;
    private Category category;
    private Team team;

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
    }

    @Test
    @DisplayName("worker 저장 테스트")
    void worker_저장() {
        // given
        WorkerAddReq workerAddReq =
                WorkerAddReq.builder().cardId(TEST_CARD_ID).addUsername(TEST_ANOTHER_NAME).build();

        given(cardRepository.findByCardId(anyLong())).willReturn(card);
        given(userRepository.findByUsername(any())).willReturn(TEST_USER);
        given(teamRoleRepository.findByUserUsernameAndTeamTeamId(any(), any()))
                .willReturn(TEST_TEAM_ROLE);
        given(workerRepository.save(any(Worker.class))).willReturn(TEST_WORKER);

        // when
        workerService.addWorker(workerAddReq);

        // then
        verify(cardRepository).findByCardId(any());
        verify(userRepository).findByUsername(any());
        verify(teamRoleRepository).findByUserUsernameAndTeamTeamId(any(), any());
        verify(workerRepository).save(any(Worker.class));
    }

    @Test
    @DisplayName("worker 삭제  테스트")
    void worker_삭제() {
        // given
        WorkerDeleteReq workerDeleteReq = WorkerDeleteReq.builder().cardId(TEST_CARD_ID).build();

        given(cardRepository.findByCardId(anyLong())).willReturn(card);
        given(userRepository.findByUsername(any())).willReturn(TEST_USER);
        given(teamRoleRepository.findByUserUsernameAndTeamTeamId(any(), any()))
                .willReturn(TEST_TEAM_ROLE);
        given(workerRepository.findByTeamRoleAndCard(any(), any())).willReturn(TEST_WORKER);

        // when
        workerService.deleteWorker(workerDeleteReq);

        // then
        verify(cardRepository).findByCardId(anyLong());
        verify(userRepository).findByUsername(any());
        verify(teamRoleRepository).findByUserUsernameAndTeamTeamId(any(), any());
        verify(workerRepository).findByTeamRoleAndCard(any(), any());
        verify(workerRepository).delete(any(Worker.class));
    }
}
