package com.wallet.user.dto;

import lombok.Data;


@Data
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private String phoneNumber;

}
