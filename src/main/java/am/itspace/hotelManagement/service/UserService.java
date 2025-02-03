package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.entity.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findByEmail(String email);
}
