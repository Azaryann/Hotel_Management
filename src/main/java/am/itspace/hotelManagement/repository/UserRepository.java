package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByVerificationToken(String token);
}
