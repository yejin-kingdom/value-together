package com.vt.valuetogether.domain.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vt.valuetogether.domain.user.entity.InviteCode;
import com.vt.valuetogether.domain.user.repository.InviteRepository;
import com.vt.valuetogether.test.InviteCodeTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InviteCodeServiceImplTest implements InviteCodeTest {
    @InjectMocks private InviteCodeServiceImpl inviteCodeService;

    @Mock private InviteRepository inviteRepository;

    @Test
    @DisplayName("code로 inviteCode 조회 테스트")
    void code_invite_code_조회() {
        // given
        when(inviteRepository.findById(any())).thenReturn(TEST_INVITE_CODE);

        // when
        InviteCode inviteCode = inviteCodeService.findById(TEST_INVITE_CODE_CODE);

        // then
        assertThat(inviteCode.getCode()).isEqualTo(TEST_INVITE_CODE_CODE);
        assertThat(inviteCode.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(inviteCode.getTeamId()).isEqualTo(TEST_TEAM_ID);
        verify(inviteRepository).findById(any());
    }
}
