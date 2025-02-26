package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify Your Email";
        String verificationUrl = "http://localhost:8080/verify?token=" + token;
        String message = "Click the link to verify your email: " + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}