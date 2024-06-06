package org.example.expense_tracker_application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Creates a new instance of BCryptPasswordEncoder
        // BCrypt is a password hashing function designed for secure password storage
        // It includes a salt to protect against rainbow table attacks and is computationally intensive to slow down brute-force attacks

    }
}

