package com.wallet.user;

import com.wallet.user.model.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
