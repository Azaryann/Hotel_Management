package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.entity.User;

import java.util.Optional;

public interface UserService {

    void registerUser(String username, String password);

    Optional<User> findByUsername(String username);
}
