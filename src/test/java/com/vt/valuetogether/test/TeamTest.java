package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.team.entity.Team;

public interface TeamTest {

    Long TEST_TEAM_ID = 1L;
    String TEST_TEAM_NAME = "team";
    String TEST_EDIT_TEAM_NAME = "teamEdit";
    String TEST_TEAM_DESCRIPTION = "desc";
    String TEST_EDIT_TEAM_DESCRIPTION = "asc";
    String TEST_BACKGROUND_COLOR = "#FFFFFF";
    String TEST_EDIT_BACKGROUND_COLOR = "#DDDDDD";
    boolean TEST_TEAM_IS_DELETED = false;

    Team TEST_TEAM =
            Team.builder()
                    .teamId(TEST_TEAM_ID)
                    .teamName(TEST_TEAM_NAME)
                    .teamDescription(TEST_TEAM_DESCRIPTION)
                    .backgroundColor(TEST_BACKGROUND_COLOR)
                    .isDeleted(TEST_TEAM_IS_DELETED)
                    .build();

    Team TEST_EDIT_TEAM =
            Team.builder()
                    .teamId(TEST_TEAM_ID)
                    .teamName(TEST_EDIT_TEAM_NAME)
                    .teamDescription(TEST_EDIT_TEAM_DESCRIPTION)
                    .backgroundColor(TEST_EDIT_BACKGROUND_COLOR)
                    .isDeleted(TEST_TEAM_IS_DELETED)
                    .build();
}
