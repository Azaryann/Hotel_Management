package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

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
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        users.forEach(System.out::println);
//        users.forEach(user -> {
//            System.out.println("User: " + user.getEmail() + ", Roles: " + user.getRole());
//        });
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUsersForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/users/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        userService.updateUser(user);
        return "redirect:/login";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
