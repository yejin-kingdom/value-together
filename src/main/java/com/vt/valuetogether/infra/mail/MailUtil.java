package com.vt.valuetogether.infra.mail;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_CODE;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_EMAIL;

import com.vt.valuetogether.domain.user.entity.EmailAuth;
import com.vt.valuetogether.domain.user.service.EmailAuthService;
import com.vt.valuetogether.global.exception.GlobalException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;
    private final EmailAuthService emailService;

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
            log.warn(e.getMessage());
        }
    }

    public void checkCode(String email, String code) {
        EmailAuth emailAuth = getEmailAuth(email);

        if (!emailAuth.getCode().equals(code)) {
            throw new GlobalException(INVALID_CODE);
        }

        emailService.delete(email);
        EmailAuth newEmailAuth = EmailAuth.builder().email(email).code(code).isChecked(true).build();

        emailService.save(newEmailAuth);
    }

    public EmailAuth getEmailAuth(String email) {
        return emailService.findById(email).orElseThrow(() -> new GlobalException(NOT_FOUND_EMAIL));
    }

    private MimeMessage createMessage(String to, String subject, String code)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(
                "http://localhost:8080/api/v1/users/confirm-email?email=" + to + "&authCode=" + code,
                "UTF-8",
                "html");

        return message;
    }
}
