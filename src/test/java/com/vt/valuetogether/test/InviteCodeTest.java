package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.user.entity.InviteCode;
import java.util.UUID;

public interface InviteCodeTest extends UserTest, TeamTest {
    String TEST_INVITE_CODE_CODE = UUID.randomUUID().toString().substring(0, 8);

    InviteCode TEST_INVITE_CODE =
            InviteCode.builder()
                    .code(TEST_INVITE_CODE_CODE)
                    .userId(TEST_USER_ID)
                    .teamId(TEST_TEAM_ID)
                    .build();
}
