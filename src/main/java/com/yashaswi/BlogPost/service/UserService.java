package com.yashaswi.BlogPost.service;

import com.yashaswi.BlogPost.dto.UserRegistrationDTO;
import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.mapper.EntityToDtoMapper;
import com.yashaswi.BlogPost.model.Role;
import com.yashaswi.BlogPost.model.User;
import com.yashaswi.BlogPost.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        User newUser = User.builder().name(registrationDTO.getName()).userName(registrationDTO.getUserName()).password(passwordEncoder.encode(registrationDTO.getPassword())).role(Role.USER).build();
        User savedUser = userRepository.save(newUser);
        return EntityToDtoMapper.toDto(savedUser);
    }
}