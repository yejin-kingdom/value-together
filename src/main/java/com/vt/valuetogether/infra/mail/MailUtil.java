package com.vt.valuetogether.infra.mail;

import static com.vt.valuetogether.global.meta.ResultCode.EMAIL_SEND_FAILED;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.entity.InviteCode;
import com.vt.valuetogether.domain.user.service.EmailAuthService;
import com.vt.valuetogether.domain.user.service.InviteCodeService;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.validator.MailValidator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;
    private final EmailAuthService emailService;
    private final InviteCodeService inviteCodeService;

    private static final String EMAIL_LINK = "http://localhost:8080/api/v1/users/signup/email/check?";
    private static final String INVITE_EMAIL_LINK =
            "http://localhost:8080/api/v1/teams/members/email?";
    private static final String PATH_AND = "&";
    private static final String PATH_KEY_EMAIL = "email=";
    private static final String PATH_KEY_CODE = "authCode=";

    @Value("${spring.mail.username}")
    private String email;

    public String createAuthCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void sendMessage(String to, String subject) {
        try {
            String code = createAuthCode();
            MimeMessage message = createMessage(to, subject, code);

            if (emailService.hasMail(to)) {
                emailService.delete(to);
            }
            EmailAuth emailAuth = EmailAuth.builder().email(to).code(code).build();

            emailService.save(emailAuth);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new GlobalException(EMAIL_SEND_FAILED);
        }
    }

    public void sendInviteMessage(String to, String subject, Long teamId, Long userId) {
        try {
            String code = createAuthCode();
            MimeMessage message = createInviteMessage(to, subject, code);

            InviteCode inviteCode = InviteCode.builder().teamId(teamId).userId(userId).code(code).build();

            inviteCodeService.save(inviteCode);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new GlobalException(EMAIL_SEND_FAILED);
        }
    }

    public void checkCode(String email, String code) {
        EmailAuth emailAuth = getEmailAuth(email);

        MailValidator.checkCode(emailAuth.getCode(), code);

        emailService.delete(email);
        EmailAuth newEmailAuth = EmailAuth.builder().email(email).code(code).isChecked(true).build();

        emailService.save(newEmailAuth);
    }

    public void checkInviteCode(String inviteCode, String code) {
        MailValidator.checkCode(inviteCode, code);
    }

    public EmailAuth getEmailAuth(String email) {
        EmailAuth emailAuth = emailService.findById(email);
        MailValidator.validate(emailAuth);
        return emailAuth;
    }

    private MimeMessage createMessage(String to, String subject, String code)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject, StandardCharsets.UTF_8.name());
        message.setContent(
                EMAIL_LINK + PATH_KEY_EMAIL + to + PATH_AND + PATH_KEY_CODE + code,
                ContentType.TEXT_HTML.getMimeType());

        return message;
    }

    private MimeMessage createInviteMessage(String to, String subject, String code)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject, StandardCharsets.UTF_8.name());
        message.setContent(
                INVITE_EMAIL_LINK + PATH_KEY_EMAIL + to + PATH_AND + PATH_KEY_CODE + code,
                ContentType.TEXT_HTML.getMimeType());

        return message;
    }
}
