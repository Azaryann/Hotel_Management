package am.itspace.hotelManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/templates/**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/verify").permitAll()

                                .requestMatchers("/rooms/**").permitAll()
                                .requestMatchers("/hotels/**").permitAll()
                                .requestMatchers("/hotels/create").permitAll()

                                .requestMatchers("/users").permitAll()
                                .requestMatchers("/users/edit/**").hasRole("ADMIN")
                                .requestMatchers("/users/update").hasRole("ADMIN")
                                .requestMatchers("/users/delete/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}
