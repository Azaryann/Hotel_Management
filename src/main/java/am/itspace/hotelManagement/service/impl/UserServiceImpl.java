package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.Role;
import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.exception.EmailAlreadyExistsException;
import am.itspace.hotelManagement.exception.UserNotFoundException;
import am.itspace.hotelManagement.mapper.UserMapper;
import am.itspace.hotelManagement.repository.RoleRepository;
import am.itspace.hotelManagement.repository.UserRepository;
import am.itspace.hotelManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailServiceImpl emailService;

    @Transactional
    public void saveUser(UserDto userDto) {
        log.info("Saving user with email: {}", userDto.getEmail());
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            log.warn("Email already exists: {}", userDto.getEmail());
            throw new EmailAlreadyExistsException("Please verify your email");
        }
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
        log.info("User saved successfully with email: {}", userDto.getEmail());
    }

    public boolean verifyUser(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        log.info("Verifying user with token: {}", token);
        if (userOptional.isEmpty()){
            log.warn("Invalid verification token: {}", token);
            return false;
        }

        User user = userOptional.get();
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        log.info("User verified successfully with token: {}", token);
        return true;
    }

    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        log.info("Updating user with id: {}", userDto.getId());
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userDto.getId()));

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
        log.info("User updated successfully with id: {}", userDto.getId());
    }

    public List<UserDto> findAllUsers() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        log.info("Deleting user by id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }
}
