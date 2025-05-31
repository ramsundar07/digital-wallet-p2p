package com.wallet.user;

import com.wallet.user.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.wallet.user.repository")
public class UserApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(UserApplication.class, args);
    }



}
