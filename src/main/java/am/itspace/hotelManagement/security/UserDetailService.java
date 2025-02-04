package am.itspace.hotelManagement.security;

import am.itspace.hotelManagement.entity.User;
import am.itspace.hotelManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byEmail = userService.findByEmail(username);
        if (byEmail.isPresent()) {
            User userFromDb = byEmail.get();
            return new SpringUser(userFromDb);
        }
        throw new UsernameNotFoundException("User with " + username + " does not exists");
    }

}
