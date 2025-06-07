package com.wallet.user.service.impl;

import com.wallet.user.dto.UserRequestDTO;
import com.wallet.user.dto.UserResponseDTO;
import com.wallet.user.exception.UserAlreadyExistException;
import com.wallet.user.exception.UserNotFoundException;
import com.wallet.user.mapper.UserDTOMapper;
import com.wallet.user.model.User;
import com.wallet.user.repository.UserRepository;
import com.wallet.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(PasswordEncoder passwordEncoder , UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    /**
     * @param userRequestDTO
     * @return
     */
    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        logger.info("Attempting to create user with email: {}", userRequestDTO.getEmail());
        User user = new User();
        UserDTOMapper.mapToUserEntity(userRequestDTO,user);
        Optional<User> existingUser = repository.findByEmailOrPhoneNumber(user.getEmail(),user.getPhoneNumber());
        if(existingUser.isPresent()) {
            logger.warn("User creation failed: Email or phone number already exists for user: {}", userRequestDTO.getEmail());
            throw new UserAlreadyExistException(String.format("Customer already exists with this mobile number : {%s} or email : {%s}" , user.getPhoneNumber() , user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        repository.save(user);
        logger.info("User created successfully with ID: {}", user.getId());
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user,userResponseDTO);
        return userResponseDTO;
    }

    /**
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO fetchUserByEmail(String email) {
        logger.debug("Fetching user by email: {}", email);
        User user = repository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("email",email)
        );
        UserResponseDTO userResponseDTO= new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user, userResponseDTO);
        logger.debug("User found by email: {}", email);
        return userResponseDTO;
    }

    /**
     * @param currentUserId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO getUserById(Long currentUserId) {
        logger.debug("Fetching user by ID: {}", currentUserId);
        User user = repository.findById(currentUserId).orElseThrow(
                ()-> new UserNotFoundException("id",currentUserId.toString())
        );
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user,userResponseDTO);
        logger.debug("User found by ID: {}", currentUserId);
        return userResponseDTO;
    }

    /**
     * @param currentUserId
     * @return
     */
    @Transactional
    @Override
    public UserResponseDTO updateUser(Long currentUserId, UserRequestDTO requestDTO) {
        logger.info("Attempting to update user with ID: {}", currentUserId);

        User user = repository.findById(currentUserId).orElseThrow(
                ()-> {
                    logger.warn("User not found for update with ID: {}", currentUserId);
                    return new UserNotFoundException("id" , currentUserId.toString());
                });
        if(requestDTO.getEmail()!=null && !requestDTO.getEmail().equalsIgnoreCase(user.getEmail())){
            Optional<User> existingUser = repository.findByEmail(requestDTO.getEmail());
            if(existingUser.isPresent()){
                logger.warn("Update failed for user ID {}: New email '{}' already taken by another user.", currentUserId, requestDTO.getEmail());
                throw new UserAlreadyExistException(String.format("Email : %s is already taken by another user",requestDTO.getEmail()));
            }
        }

        UserDTOMapper.mapToUserEntity(requestDTO,user);
        if(requestDTO.getPassword()!=null && !requestDTO.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        repository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserDTOMapper.mapToUserResponseDto(user,userResponseDTO);
        return userResponseDTO;
    }

    /**
     * @param currentUserId
     */
    @Transactional
    @Override
    public void deleteUser(Long currentUserId) {
        logger.info("Attempting to delete user with ID: {}", currentUserId);
        boolean isDeleted = false;
        User user = repository.findById(currentUserId).orElseThrow(
                ()-> new UserNotFoundException("id",currentUserId.toString())
        );
        repository.deleteById(user.getId());

        logger.info("User with ID {} deleted successfully.", currentUserId);
    }
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> getAllUsers(){
        logger.debug("Fetching all users.");
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        return repository.findAll().stream().map(user->{
            UserResponseDTO userResponseDTO =  new UserResponseDTO();
            UserDTOMapper.mapToUserResponseDto(user,userResponseDTO);
            return userResponseDTO;
        }).collect(Collectors.toList());
    }

}
