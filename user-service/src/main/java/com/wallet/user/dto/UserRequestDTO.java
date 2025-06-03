package com.wallet.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "User Account",
        description = "Holds User account details"
)
public class UserRequestDTO {
    
    @NotEmpty(message = "Username can't be empty")
    private String userName;
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email ID is not valid")
    private String email;
    @NotEmpty(message = "Phone Number cannot be empty")
    @Pattern(regexp = "\\d{10}",message = "Phone Number should have 10 digits")
    private String phoneNumber;
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
