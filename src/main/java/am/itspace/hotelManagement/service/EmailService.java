package am.itspace.hotelManagement.service;

public interface EmailService {

    void sendVerificationEmail(String to, String token);

}
