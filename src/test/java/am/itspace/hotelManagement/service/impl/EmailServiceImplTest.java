package am.itspace.hotelManagement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendVerificationEmail() {
        String to = "test@example.com";
        String token = "12345";
        String subject = "Verify Your Email";
        String verificationUrl = "http://localhost:8080/verify?token=" + token;
        String message = "Click the link to verify your email: " + verificationUrl;

        emailService.sendVerificationEmail(to, token);

        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(emailCaptor.capture());

        SimpleMailMessage email = emailCaptor.getValue();
        assertEquals(to, Objects.requireNonNull(email.getTo())[0]);
        assertEquals(subject, email.getSubject());
        assertEquals(message, email.getText());
    }
}