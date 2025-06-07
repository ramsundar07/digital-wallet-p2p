package com.wallet.user.controller;

import com.wallet.commondtos.dto.ErrorResponseDTO;
import com.wallet.commondtos.dto.ResponseDTO;
import com.wallet.user.constants.UserConstants;
import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "User Management",
        description = "APIs for managing user accounts and profiles within the Digital Wallet P2P application."
)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Creates a New User account",
            description = "Registers a new user with the given details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",content =
                    {
                         @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                         )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "User already exist with the given phone number or email",content =
                    {
                            @Content(schema =
                                @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

    @PostMapping
    public ResponseEntity<ResponseDTO<UserResponseDTO>> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Received request to create user with email: {}", userRequestDTO.getEmail());
        UserResponseDTO userResponseDTO =  userService.createUser(userRequestDTO);
        logger.info("User creation request processed for email: {}", userRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(UserConstants.STATUS_201 , UserConstants.MESSAGE_201,userResponseDTO));

    }

    @Operation(
            summary = "Get User details by email Id",
            description = "Retrieves details of specific user based on email id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with given email id fetched successfully",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found with the given email id",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

    @GetMapping
    public ResponseEntity<UserResponseDTO> fetchUserByEmail(@RequestParam @NotEmpty(message = "Email can't be empty") @Email( message = "Email id is not Valid") String email){
        logger.info("Received request to fetch user by email: {}", email);
        UserResponseDTO userResponseDTO = userService.fetchUserByEmail(email);
        logger.info("User fetched by email: {}", email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @Operation(
            summary = "Get User details by User Id",
            description = "Retrieves details of specific user based on User id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with given id fetched successfully",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found with the given id",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

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

    @Operation(
            summary = "Updates an User account",
            description = "Modifies the user account details based on given input"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with given details has been updated successfully",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found with the given detail",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "User already exist with the same email address that is mentioned",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

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

    @Operation(
            summary = "Deletes an User account",
            description = "Deletes the specific user account details based on id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User with given details has been deleted successfully",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found with the given detail",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

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

    @Operation(
            summary = "Get all User accounts",
            description = "Retrieves a list of all registered user accounts."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Users have been fetched successfully",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ResponseDTO.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content =
                    {
                            @Content(schema =
                            @Schema(implementation = ErrorResponseDTO.class)
                            )
                    })
    })

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        logger.info("Received request to fetch all users."); // ADDED: Logging
        List<UserResponseDTO> users = userService.getAllUsers();
        logger.info("Fetched {} users.", users.size()); // ADDED: Logging
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(UserConstants.STATUS_200, UserConstants.MESSAGE_200,users));
    }


}
