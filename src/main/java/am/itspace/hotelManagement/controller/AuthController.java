package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.entity.UserRole;
import am.itspace.hotelManagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("roles", Set.of(UserRole.CUSTOMER, UserRole.ADMIN));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.valueOf(role));
        userService.save(user);
        return "redirect:/login";
    }
}
