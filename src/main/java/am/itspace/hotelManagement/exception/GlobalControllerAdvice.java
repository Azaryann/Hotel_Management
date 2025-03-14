package am.itspace.hotelManagement.exception;

import am.itspace.hotelManagement.utils.AuthUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("authenticatedUser")
    public String addAuthenticatedUserToModel() {
        return AuthUtils.getAuthenticatedUsername();
    }
}