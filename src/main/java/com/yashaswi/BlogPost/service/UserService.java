package com.yashaswi.BlogPost.service;

import com.yashaswi.BlogPost.dto.UserRegistrationDTO;
import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.exception.DuplicateResponseException;
import com.yashaswi.BlogPost.exception.UserNotFoundException;
import com.yashaswi.BlogPost.mapper.EntityToDtoMapper;
import com.yashaswi.BlogPost.model.Role;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.findByUserName(registrationDTO.getUserName()).isPresent()) {
            throw new DuplicateResponseException("Username " + registrationDTO.getUserName() + " is already taken.");
        }
        User newUser = User.builder().name(registrationDTO.getName()).userName(registrationDTO.getUserName()).password(passwordEncoder.encode(registrationDTO.getPassword())).role(Role.USER).build();
        User savedUser = userRepository.save(newUser);
        return EntityToDtoMapper.toDto(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(EntityToDtoMapper::toDto).collect(Collectors.toList());
    }

    public UserResponseDTO getProfile(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException(username));
        return EntityToDtoMapper.toDto(user);
    }

    public UserResponseDTO updateProfile(String username, UserResponseDTO userResponseDTO) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException(username));
        if (userResponseDTO.getName() != null) {
            user.setName(userResponseDTO.getName());
        }
        if (userResponseDTO.getUserName() != null) {
            user.setUserName(userResponseDTO.getUserName());
        }
        userRepository.save(user);
        return EntityToDtoMapper.toDto(user);
    }
}