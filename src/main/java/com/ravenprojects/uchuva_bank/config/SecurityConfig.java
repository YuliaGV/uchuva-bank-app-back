package com.ravenprojects.uchuva_bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object to configure.
     * @return The built SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for API development
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll() // Allow all requests without authentication
                )
                .httpBasic(withDefaults()); // Enable HTTP Basic authentication (optional, but good for testing)
        return http.build();
    }

}
