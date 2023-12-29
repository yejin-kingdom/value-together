package com.vt.valuetogether.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.user.dto.request.UserCheckDuplicateUsernameReq;
import com.vt.valuetogether.domain.user.dto.request.UserSignupReq;
import com.vt.valuetogether.domain.user.dto.request.UserUpdateProfileReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyEmailReq;
import com.vt.valuetogether.domain.user.dto.request.UserVerifyPasswordReq;
import com.vt.valuetogether.domain.user.dto.response.UserCheckDuplicateUsernameRes;
import com.vt.valuetogether.domain.user.dto.response.UserConfirmEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserGetProfileRes;
import com.vt.valuetogether.domain.user.dto.response.UserSignupRes;
import com.vt.valuetogether.domain.user.dto.response.UserUpdateProfileRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyEmailRes;
import com.vt.valuetogether.domain.user.dto.response.UserVerifyPasswordRes;
import com.vt.valuetogether.domain.user.service.UserService;
import com.vt.valuetogether.global.MockSpringSecurityFilter;
import com.vt.valuetogether.global.config.WebSecurityConfig;
import com.vt.valuetogether.global.security.UserDetailsImpl;
import com.vt.valuetogether.test.UserTest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
        })
class UserControllerTest extends BaseMvcTest implements UserTest {

    @MockBean private UserService userService;

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .apply(springSecurity(new MockSpringSecurityFilter()))
                        .build();
    }

    private void mockUserSetup() {
        UserDetails testUserDetails = new UserDetailsImpl(TEST_USER);
        mockPrincipal =
                new UsernamePasswordAuthenticationToken(
                        testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("이메일 전송 api 테스트")
    void sendEmailTest() throws Exception {
        // given
        UserVerifyEmailReq req = UserVerifyEmailReq.builder().email(TEST_USER_EMAIL).build();
        UserVerifyEmailRes res = new UserVerifyEmailRes();
        when(userService.sendEmail(req)).thenReturn(res);

        // when - then
        mvc.perform(
                        post("/api/v1/users/signup/email")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일 링크 확인 api 테스트")
    void confirmEmailTest() throws Exception {
        // given
        String email = "abcd@ab.com";
        String code = "abdEfs";

        UserConfirmEmailRes res = UserConfirmEmailRes.builder().email(email).build();
        when(userService.confirmEmail(email, code)).thenReturn(res);

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("email", email);
        info.add("authCode", code);

        // when - then
        mvc.perform(get("/api/v1/users/signup/email/check").params(info))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 api 테스트")
    void signupTest() throws Exception {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .email(TEST_USER_EMAIL)
                        .build();
        UserSignupRes res = new UserSignupRes();
        when(userService.signup(req)).thenReturn(res);

        // when - then
        mvc.perform(
                        post("/api/v1/users/signup")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("username 중복확인 api 테스트")
    void confirmUsernameTest() throws Exception {
        // given
        UserCheckDuplicateUsernameReq req =
                UserCheckDuplicateUsernameReq.builder().username(TEST_USER_NAME).build();
        UserCheckDuplicateUsernameRes res =
                UserCheckDuplicateUsernameRes.builder().isDuplicated(true).build();
        when(userService.checkDuplicateUsername(req)).thenReturn(res);

        // when - then
        mvc.perform(
                        post("/api/v1/users/username")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("password 확인 api 테스트")
    void verifyPasswordTest() throws Exception {
        // given
        mockUserSetup();
        UserVerifyPasswordReq req =
                UserVerifyPasswordReq.builder().password(TEST_USER_PASSWORD).build();
        UserVerifyPasswordRes res = UserVerifyPasswordRes.builder().isMatched(true).build();
        when(userService.verifyPassword(req)).thenReturn(res);

        // when - then
        mvc.perform(
                        post("/api/v1/users/password/verify")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("프로필 수정 api 테스트")
    void updateProfileTest() throws Exception {
        // given
        mockUserSetup();

        String imageUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());

        UserUpdateProfileReq request =
                UserUpdateProfileReq.builder()
                        .username(TEST_ANOTHER_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .introduce(TEST_USER_INTRODUCE)
                        .build();

        MockMultipartFile req =
                new MockMultipartFile(
                        "data",
                        null,
                        "application/json",
                        objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));

        UserUpdateProfileRes res = new UserUpdateProfileRes();
        when(userService.updateProfile(request, file)).thenReturn(res);

        // when - then
        mvc.perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/api/v1/users")
                                .file(file)
                                .file(req)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("프로필 조회 api 테스트")
    void getProfileTest() throws Exception {
        // given
        UserGetProfileRes res =
                UserGetProfileRes.builder()
                        .userId(TEST_USER_ID)
                        .username(TEST_USER_NAME)
                        .email(TEST_USER_EMAIL)
                        .introduce(TEST_USER_INTRODUCE)
                        .profileImageUrl(TEST_USER_PROFILE_URL)
                        .build();
        when(userService.getProfile(TEST_USER_ID)).thenReturn(res);

        // when - then
        mvc.perform(get("/api/v1/users/{userId}", TEST_USER_ID))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
