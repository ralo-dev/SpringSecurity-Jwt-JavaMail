package dev.ralo.example.email;

import dev.ralo.example.registration.ConfirmationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;
    private static final ClassPathResource TEMPLATE = new ClassPathResource("static/templates/confirmation_template.html");

    @Value("${spring.mail.username}")
    private String from;
    private static final String url = "http://localhost:8080/api/auth/confirm?token=";

    @Override
    public void send(ConfirmationToken confirmationToken) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            InputStream inputStream = TEMPLATE.getInputStream();
            String html = new String(inputStream.readAllBytes());
            html = html.replace("$USERNAME", confirmationToken.getUserEntity().getUsername());
            html = html.replace("$LINK", url+confirmationToken.getToken());

            helper.setText(html, true);
            helper.setTo(confirmationToken.getUserEntity().getEmail());
            helper.setSubject("Confirm your email");
            helper.setFrom(from);
            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            log.error("failed to send email", e);
            throw new RuntimeException(e);
        }
    }
}
