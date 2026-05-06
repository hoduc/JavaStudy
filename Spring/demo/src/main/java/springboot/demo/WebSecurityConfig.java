package springboot.demo;

import java.beans.BeanProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import springboot.demo.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "springboot.demo")
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/greeting", "/home").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form 
                .loginPage("/login")
                .permitAll()
            )
            .logout(LogoutConfigurer::permitAll).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
}
