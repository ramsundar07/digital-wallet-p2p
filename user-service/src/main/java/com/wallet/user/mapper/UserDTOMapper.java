package com.wallet.user.mapper;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.model.User;

public class UserDTOMapper {

    /**
     * @param userRequestDTO
     * @param user
     */
    public static void mapToUserEntity(UserRequestDTO userRequestDTO, User user){
        user.setUserName(userRequestDTO.getUserName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
    }

    /**
     * @param user
     * @param userResponseDTO
     */
    public static void mapToUserResponseDto(User user, UserResponseDTO userResponseDTO){
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUserName(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
    }
}
