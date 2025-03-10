package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final String MESSAGE="Click the link to verify your email: ";

    @Value("${mailing.host}")
    private String host;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify Your Email";
        String verificationUrl = host + token;
        String message = MESSAGE + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}