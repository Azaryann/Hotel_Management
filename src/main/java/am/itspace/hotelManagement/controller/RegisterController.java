package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.entity.UserRole;
import am.itspace.hotelManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam UserRole role) {

        if (userService.findByEmail(username).isPresent()) {
            return "redirect:/register?error=Username already exists";
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserRole(role);
        userService.save(user);
        return "redirect:/login";
    }
}
