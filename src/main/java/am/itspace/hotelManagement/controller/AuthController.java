package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.CreateUserDto;
import am.itspace.hotelManagement.dto.UpdateUserDto;
import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "index"})
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UpdateUserDto user = new UpdateUserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") CreateUserDto createUserDto,
                               BindingResult result, Model model) {
        User existing = userService.findByEmail(createUserDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", createUserDto);
            return "register";
        }
        try {
            userService.saveUser(createUserDto);
            model.addAttribute("success", "A verification email has been sent!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        userService.saveUser(createUserDto);
        return "redirect:/register?success";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model) {
        boolean isVerified = userService.verifyUser(token);
        model.addAttribute("message", isVerified ? "Your email has been verified!" : "Invalid verification link.");
        return "verification-result";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UpdateUserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUsersForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PutMapping("/users/update")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserDto user) {
        userService.updateUser(user);
        return "redirect:/login";
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
