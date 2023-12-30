package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.team.entity.Role;
import com.vt.valuetogether.domain.team.entity.TeamRole;

public interface TeamRoleTest extends TeamTest, UserTest {
    Long TEST_TEAM_ROLE_ID = 1L;
    Role TEST_TEAM_ROLE_ROLE = Role.LEADER;
    boolean TEST_TEAM_ROLE_IS_DELETED = false;

    TeamRole TEST_TEAM_ROLE =
            TeamRole.builder()
                    .teamRoleId(TEST_TEAM_ROLE_ID)
                    .user(TEST_USER)
                    .team(TEST_TEAM)
                    .role(TEST_TEAM_ROLE_ROLE)
                    .isDeleted(TEST_TEAM_ROLE_IS_DELETED)
                    .build();
}
