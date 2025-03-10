package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.CreateUserDto;
import am.itspace.hotelManagement.dto.UpdateUserDto;
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
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailServiceImpl emailService;

    @Transactional
    public void saveUser(CreateUserDto createUserDto) {
        log.info("Saving user with email: {}", createUserDto.getEmail());
        if (userRepository.findByEmail(createUserDto.getEmail()) != null) {
            log.warn("Email already exists: {}", createUserDto.getEmail());
            throw new EmailAlreadyExistsException("Please verify your email");
        }
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setEnabled(false);
        Role role = roleRepository.findByName(createUserDto.getRole());
        if (role == null) {
            role = new Role();
            role.setName(createUserDto.getRole());
            role = roleRepository.save(role);
        }
        user.setRoles(Collections.singletonList(role));
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        userRepository.save(user);
        emailService.sendVerificationEmail(createUserDto.getEmail(), verificationToken);
        log.info("User saved successfully with email: {}", createUserDto.getEmail());
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
    public void updateUser(UpdateUserDto updateUserDto) {
        log.info("Updating user with id: {}", updateUserDto.getId());
        User user = userRepository.findById(updateUserDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + updateUserDto.getId()));

        if (updateUserDto.getFirstName() != null) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        if (updateUserDto.getRole() != null) {
            Role role = roleRepository.findByName(updateUserDto.getRole());
            if (role == null) {
                role = new Role();
                role.setName(updateUserDto.getRole());
                role = roleRepository.save(role);
            }
            user.setRoles(Collections.singletonList(role));
        }
        userRepository.save(user);
        log.info("User updated successfully with id: {}", updateUserDto.getId());
    }

    public List<UpdateUserDto> findAllUsers() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUpdateDTO)
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
