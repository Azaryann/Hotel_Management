package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto>findAllUsers();

    User findById(Long id);

    void updateUser(UserDto userDto);

    void deleteUserById(Long id);
}