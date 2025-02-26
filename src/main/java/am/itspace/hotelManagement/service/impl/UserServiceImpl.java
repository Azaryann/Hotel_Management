package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.Role;
import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.mapper.UserMapper;
import am.itspace.hotelManagement.repository.RoleRepository;
import am.itspace.hotelManagement.repository.UserRepository;
import am.itspace.hotelManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailServiceImpl emailService;

    @Transactional
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(false);
        Role role = roleRepository.findByName(userDto.getRole());
        if (role == null) {
            role = new Role();
            role.setName(userDto.getRole());
            role = roleRepository.save(role);
        }
        user.setRoles(Collections.singletonList(role));
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        userRepository.save(user);
        emailService.sendVerificationEmail(userDto.getEmail(), verificationToken);
    }

    public boolean verifyUser(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        if (userOptional.isEmpty()) return false;

        User user = userOptional.get();
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return true;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Not Found"));
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getRole() != null) {
            Role role = roleRepository.findByName(userDto.getRole());
            if (role == null) {
                role = new Role();
                role.setName(userDto.getRole());
                role = roleRepository.save(role);
            }
            user.setRoles(Collections.singletonList(role));
        }
        userRepository.save(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
