package com.vt.valuetogether.domain.team.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.team.dto.reponse.TeamCreateRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamGetRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberDeleteRes;
import com.vt.valuetogether.domain.team.dto.reponse.TeamMemberInviteRes;
import com.vt.valuetogether.domain.team.dto.request.TeamCreateReq;
import com.vt.valuetogether.domain.team.dto.request.TeamDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberDeleteReq;
import com.vt.valuetogether.domain.team.dto.request.TeamMemberInviteReq;
import com.vt.valuetogether.domain.team.service.TeamService;
import com.vt.valuetogether.test.TeamTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest extends BaseMvcTest implements TeamTest {

    @MockBean private TeamService teamService;

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

        given(teamService.getTeamInfo(anyLong(), anyString())).willReturn(res);
        // when, then
        mockMvc
                .perform(get("/api/v1/teams/" + TEST_TEAM_ID).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 생성 테스트")
    void team_생성() throws Exception {
        // given
        TeamCreateReq req =
                TeamCreateReq.builder()
                        .teamName(TEST_TEAM_NAME)
                        .teamDescription(TEST_TEAM_DESCRIPTION)
                        .backgroundColor(TEST_BACKGROUND_COLOR)
                        .build();

        TeamCreateRes res = TeamCreateRes.builder().teamId(TEST_TEAM_ID).build();

        given(teamService.createTeam(any())).willReturn(res);
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
    @DisplayName("팀 초대 테스트")
    void team_초대() throws Exception {
        // given
        TeamMemberInviteReq req =
                TeamMemberInviteReq.builder()
                        .teamId(TEST_TEAM_ID)
                        .memberNameList(List.of("testMember"))
                        .build();

        TeamMemberInviteRes res = new TeamMemberInviteRes();

        given(teamService.inviteMember(req)).willReturn(res);

        // when, then
        mockMvc
                .perform(
                        post("/api/v1/teams/members")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 삭제 테스트")
    void team_삭제() throws Exception {
        // given
        TeamDeleteReq req = TeamDeleteReq.builder().teamId(TEST_TEAM_ID).build();
        TeamDeleteRes res = new TeamDeleteRes();

        given(teamService.deleteTeam(any(TeamDeleteReq.class))).willReturn(res);

        // when, then
        mockMvc
                .perform(
                        delete("/api/v1/teams")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 멤버 삭제 테스트")
    void team_member_삭제() throws Exception {
        // given
        TeamMemberDeleteReq req =
                TeamMemberDeleteReq.builder().teamId(TEST_TEAM_ID).memberName("testMember").build();

        TeamMemberDeleteRes res = new TeamMemberDeleteRes();

        given(teamService.deleteMember(any(TeamMemberDeleteReq.class))).willReturn(res);

        // when, then
        mockMvc
                .perform(
                        delete("/api/v1/teams/members")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
