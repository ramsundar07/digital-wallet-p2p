package com.wallet.user.service;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.exception.RoleNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    /**
     * @param userRequestDTO
     * @return
     */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws RoleNotFoundException;

    /**
     * @param email
     * @return
     */
    UserResponseDTO fetchUserByEmail(String email);

    UserResponseDTO getUserById(Long currentUserId);

    @Transactional(readOnly = true)
    UserResponseDTO getUserByUserName(String userName);

    UserResponseDTO updateUser(Long currentUserId, UserRequestDTO requestDTO);

    void deleteUser(Long currentUserId);

    List<UserResponseDTO> getAllUsers();

    public Long getAuthenticatedUserId() throws Exception;
}