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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class UserController {
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(UserConstants.STATUS_201 , UserConstants.MESSAGE_201));

    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> fetchUserByEmail(@RequestParam @NotEmpty(message = "Email can't be empty") @Email( message = "Email id is not Valid") String email){
        UserResponseDTO userResponseDTO = userService.fetchUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile() {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
        // Example: Long currentUserId = ((MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
        UserResponseDTO userResponseDTO =  userService.getUserById(currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    @PutMapping("/me")
    public ResponseEntity<ResponseDTO> updateMyProfile(
            @RequestBody UserRequestDTO requestDTO) {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
        Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
        boolean isUpdate = userService.updateUser(currentUserId , requestDTO);
        if (isUpdate) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(UserConstants.STATUS_200 , UserConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(UserConstants.STATUS_500 , UserConstants.MESSAGE_500));
        }
    }
    @DeleteMapping("/me")
    public ResponseEntity<ResponseDTO> deleteMyAccount() {
        // Placeholder for obtaining authenticated user ID.
        // In a real application, this would come from Spring Security's Authentication object.
        Long currentUserId = 1L; // !!! TEMPORARY: REPLACE WITH REAL AUTHENTICATED USER ID !!!
        boolean isDeleted =  userService.deleteUser(currentUserId);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO(UserConstants.STATUS_500 , UserConstants.MESSAGE_500));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(UserConstants.STATUS_500 , UserConstants.MESSAGE_500));
        }
    }


}
