package com.wallet.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // MODIFIED: Disable CSRF protection for API services (typical for REST APIs)
                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints: allow access to /api/users for registration (POST) and H2 console
                        // H2 console is allowed for development purposes
                        .requestMatchers("/api/users/**").permitAll() // Allow POST /api/users (for registration)
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**" // Essential for Swagger UI's static assets
                        ).permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // MODIFIED: Keep HTTP Basic for now (default Spring Security)
        // .formLogin(Customizer.withDefaults()); // Could use form login as well, but httpBasic is simpler for API clients

        // IMPORTANT: For H2 console to work with Spring Security, you might also need
        // to configure frame options for embedding in an iframe (default is deny).
        http.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable()); // MODIFIED: Disable frame options for H2 console

        return http.build();
    }
}
