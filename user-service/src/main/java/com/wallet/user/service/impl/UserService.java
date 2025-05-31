package com.wallet.user.service.impl;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.exception.UserAlreadyExistException;
import com.wallet.user.exception.UserNotFoundException;
import com.wallet.user.mapper.UserDTOMapper;
import com.wallet.user.model.User;
import com.wallet.user.repository.UserRepository;
import com.wallet.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repository;
    /**
     * @param userRequestDTO
     */
    @Override
    public void createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        UserDTOMapper.mapToUserEntity(userRequestDTO,user);
        Optional<User> existingUser = repository.findByEmailOrPhoneNumber(user.getEmail(),user.getPhoneNumber());
        if(existingUser.isPresent())
            throw new UserAlreadyExistException(String.format("Customer already exists with this mobile number : {%s} or email : {%s}", user.getPhoneNumber(),user.getEmail()));
        repository.save(user);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UserResponseDTO fetchUserByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("email",email)
        );
        UserResponseDTO userResponseDTO= new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user, userResponseDTO);
        return userResponseDTO;
    }

    /**
     * @param currentUserId
     * @return
     */
    @Override
    public UserResponseDTO getUserById(Long currentUserId) {
        User user = repository.findById(currentUserId).orElseThrow(
                ()-> new UserNotFoundException("id",currentUserId.toString())
        );
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user,userResponseDTO);
        return userResponseDTO;
    }

    /**
     * @param currentUserId
     * @return
     */
    @Override
    public boolean updateUser(Long currentUserId, UserRequestDTO requestDTO) {
        boolean isUpdated = false;
        User user = repository.findById(currentUserId).orElseThrow(
                ()-> new UserNotFoundException("id",currentUserId.toString())
        );
        UserDTOMapper.mapToUserEntity(requestDTO,user);
        repository.save(user);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * @param currentUserId
     */
    @Override
    public boolean deleteUser(Long currentUserId) {
        boolean isDeleted = false;
        User user = repository.findById(currentUserId).orElseThrow(
                ()-> new UserNotFoundException("id",currentUserId.toString())
        );
        repository.deleteById(user.getId());
        isDeleted = true;
        return isDeleted;
    }

}
