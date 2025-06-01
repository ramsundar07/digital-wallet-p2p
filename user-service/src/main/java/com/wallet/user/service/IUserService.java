package com.wallet.user.service;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {
    /**
     * @param userRequestDTO
     * @return
     */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    /**
     * @param email
     * @return
     */
    UserResponseDTO fetchUserByEmail(String email);

    UserResponseDTO getUserById(Long currentUserId);

    UserResponseDTO updateUser(Long currentUserId, UserRequestDTO requestDTO);

    void deleteUser(Long currentUserId);

    List<UserResponseDTO> getAllUsers();
}