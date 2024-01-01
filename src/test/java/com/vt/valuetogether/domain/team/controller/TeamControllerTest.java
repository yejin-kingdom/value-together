package com.vt.valuetogether.domain.team.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.test.TeamTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest extends BaseMvcTest implements TeamTest {

    @MockBean private TeamService teamService;

    @Test
    @DisplayName("팀 생성 테스트")
    void team_생성() throws Exception {
        // given
        TeamCreateReq req =
                TeamCreateReq.builder()
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .build();

        TeamCreateRes res = TeamCreateRes.builder().teamId(TEST_TEAM_ID).build();

        BDDMockito.given(teamService.createTeam(any())).willReturn(res);
        // when, then
        mockMvc
                .perform(
                        post("/api/v1/teams")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 조회 테스트")
    void team_조회() throws Exception {
        // given
        TeamGetRes res =
                TeamGetRes.builder()
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .build();

        BDDMockito.given(teamService.getTeamInfo(anyLong(), anyString())).willReturn(res);
        // when, then
        mockMvc
                .perform(get("/api/v1/teams/" + TEST_TEAM_ID).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
