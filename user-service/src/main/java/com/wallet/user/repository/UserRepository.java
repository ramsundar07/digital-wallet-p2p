package com.wallet.user.repository;

import com.wallet.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA interface
import org.springframework.stereotype.Repository; // Marks this as a Spring repository component

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByEmail(String email);
}