package com.wallet.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "User Service REST API",
                description = "User Service REST API",
                version = "v1"
        )
)
public class UserApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(UserApplication.class, args);
    }



}
