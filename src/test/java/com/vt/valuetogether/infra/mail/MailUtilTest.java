package com.vt.valuetogether.infra.mail;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.entity.InviteCode;
import com.vt.valuetogether.domain.user.service.EmailAuthService;
import com.vt.valuetogether.domain.user.service.InviteCodeService;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.test.EmailAuthTest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class MailUtilTest implements EmailAuthTest {
    @Mock JavaMailSender mailSender;

    @Mock EmailAuthService emailAuthService;

    @Mock InviteCodeService inviteCodeService;

    @InjectMocks MailUtil mailUtil;

    @Captor ArgumentCaptor<EmailAuth> emailAuthCaptor;

    @Captor ArgumentCaptor<MimeMessage> messageCaptor;

    @Captor ArgumentCaptor<InviteCode> inviteCodeCaptor;

    @Test
    @DisplayName("인증 코드 생성")
    void createAuthCodeTest() {
        // given

        // when
        String code = mailUtil.createAuthCode();

        // then
        assertEquals(8, code.length());
    }

    @Test
    @DisplayName("인증 메일 전송")
    void sendMessageTest() throws MessagingException {
        // given
        String subject = "이메일 인증";
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(System.getProperties()));

        given(emailAuthService.hasMail(TEST_EMAIL)).willReturn(true);
        given(mailSender.createMimeMessage()).willReturn(message);

        // when
        mailUtil.sendMessage(TEST_EMAIL, subject);

        // then
        verify(emailAuthService).save(emailAuthCaptor.capture());
        assertEquals(TEST_EMAIL, emailAuthCaptor.getValue().getEmail());
        verify(mailSender).send(messageCaptor.capture());
        assertEquals(subject, messageCaptor.getValue().getSubject());
    }

    @Test
    @DisplayName("초대 메일 전송")
    void sendInviteMessageTest() throws MessagingException {
        // given
        String subject = "이메일 인증";
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(System.getProperties()));

        given(mailSender.createMimeMessage()).willReturn(message);

        // when
        mailUtil.sendInviteMessage(TEST_EMAIL, subject, TEST_TEAM_ID, TEST_USER_ID);

        // then
        verify(inviteCodeService).save(inviteCodeCaptor.capture());
        assertEquals(TEST_USER_ID, inviteCodeCaptor.getValue().getUserId());
        assertEquals(TEST_TEAM_ID, inviteCodeCaptor.getValue().getTeamId());
        verify(mailSender).send(messageCaptor.capture());
        assertEquals(subject, messageCaptor.getValue().getSubject());
    }

    @Test
    @DisplayName("인증 코드 검증")
    void checkCodeTest() {
        // given
        given(emailAuthService.findById(TEST_EMAIL)).willReturn(TEST_EMAIL_AUTH);

        // when
        mailUtil.checkCode(TEST_EMAIL, TEST_CODE);

        // then
        verify(emailAuthService).save(emailAuthCaptor.capture());
        assertTrue(emailAuthCaptor.getValue().isChecked());
    }

    @Test
    @DisplayName("초대코드 확인 - 실패")
    void checkInviteCode() {
        // given

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            mailUtil.checkInviteCode(TEST_CODE, TEST_ANOTHER_CODE);
                        });

        // then
        assertEquals(INVALID_CODE.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("email로 EmailAuth 찾아 반환")
    void getEmailAuthCode() {
        // given
        given(emailAuthService.findById(TEST_EMAIL)).willReturn(TEST_EMAIL_AUTH);

        // when
        EmailAuth emailAuth = mailUtil.getEmailAuth(TEST_EMAIL);

        // then
        assertEquals(TEST_EMAIL, emailAuth.getEmail());
    }
}
