package com.wallet.user.controller;

import com.wallet.commondtos.dto.ResponseDTO;
import com.wallet.user.constants.UserConstants;
import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.service.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<ResponseDTO<UserResponseDTO>> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Received request to create user with email: {}", userRequestDTO.getEmail());
        UserResponseDTO userResponseDTO =  userService.createUser(userRequestDTO);
        logger.info("User creation request processed for email: {}", userRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(UserConstants.STATUS_201 , UserConstants.MESSAGE_201,userResponseDTO));

    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> fetchUserByEmail(@RequestParam @NotEmpty(message = "Email can't be empty") @Email( message = "Email id is not Valid") String email){
        logger.info("Received request to fetch user by email: {}", email);
        UserResponseDTO userResponseDTO = userService.fetchUserByEmail(email);
        logger.info("User fetched by email: {}", email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile() {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
        // Example: Long currentUserId = ((MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
        logger.info("Received request to get profile for current user ID: {}", currentUserId);
        UserResponseDTO userResponseDTO =  userService.getUserById(currentUserId);
        logger.info("Profile retrieved for user ID: {}", currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    @PutMapping("/me")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateMyProfile(
            @RequestBody UserRequestDTO requestDTO) {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
         Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
         logger.info("Received request to update profile for current user ID: {}", currentUserId);
         UserResponseDTO userResponseDTO = userService.updateUser(currentUserId , requestDTO);
         logger.info("Profile updated for user ID: {}", currentUserId);
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(UserConstants.STATUS_200 , UserConstants.MESSAGE_200,userResponseDTO));

    }
    @DeleteMapping("/me")
    public ResponseEntity<ResponseDTO<Void>> deleteMyAccount() {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
        Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
        logger.info("Received request to delete profile for current user ID: {}", currentUserId);
        userService.deleteUser(currentUserId);
        logger.info("Profile deleted for user ID: {}", currentUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        logger.info("Received request to fetch all users."); // ADDED: Logging
        List<UserResponseDTO> users = userService.getAllUsers();
        logger.info("Fetched {} users.", users.size()); // ADDED: Logging
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(UserConstants.STATUS_200, UserConstants.MESSAGE_200,users));
    }


}
