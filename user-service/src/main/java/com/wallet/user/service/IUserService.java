package com.wallet.user.service;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    /**
     * @param userRequestDTO
     */
    void createUser(UserRequestDTO userRequestDTO);

    /**
     * @param email
     * @return
     */
    UserResponseDTO fetchUserByEmail(String email);

    UserResponseDTO getUserById(Long currentUserId);

    boolean updateUser(Long currentUserId, UserRequestDTO requestDTO);

    boolean deleteUser(Long currentUserId);
}