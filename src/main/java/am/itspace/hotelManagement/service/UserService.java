package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.CreateUserDto;
import am.itspace.hotelManagement.dto.UpdateUserDto;
import am.itspace.hotelManagement.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(CreateUserDto createUserDto);

    User findByEmail(String email);

    List<UpdateUserDto> findAllUsers();

    User findById(Long id);

    void updateUser(UpdateUserDto updateUserDto);

    void deleteUserById(Long id);

    boolean verifyUser(String token);
}