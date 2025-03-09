package am.itspace.hotelManagement.service.impl;


import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.Role;
import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.exception.EmailAlreadyExistsException;
import am.itspace.hotelManagement.exception.UserNotFoundException;

import am.itspace.hotelManagement.repository.RoleRepository;
import am.itspace.hotelManagement.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setFirstName("aaa");
        userDto.setLastName("bbb");
        userDto.setEmail("aaabbb@example.com");
        userDto.setPassword("password");
        userDto.setRole("USER");

        user = new User();
        user.setFirstName("aaa");
        user.setLastName("bbb");
        user.setEmail("aaabbb@example.com");
        user.setPassword("encodedPassword");
        user.setEnabled(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        role = new Role();
        role.setName("USER");
    }

    @Test
    void saveUser_ShouldSaveUser_WhenEmailDoesNotExist() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(userDto.getRole())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(userDto);

        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(passwordEncoder, times(1)).encode(userDto.getPassword());
        verify(roleRepository, times(1)).findByName(userDto.getRole());
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendVerificationEmail(eq(userDto.getEmail()), anyString());
    }

    @Test
    void saveUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User foundUser = userService.findByEmail(user.getEmail());

        assertEquals(user, foundUser);
    }

    @Test
    void findById_ShouldReturnUser_WhenIdExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.findById(user.getId());

        assertEquals(user, foundUser);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(userDto.getRole())).thenReturn(role);

        userService.updateUser(userDto);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserById_ShouldDeleteUser_WhenIdExists() {
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUserById(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserById_ShouldThrowException_WhenIdDoesNotExist() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(user.getId()));
    }
}
