package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.dto.UserRegistrationDTO;
import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO){
        return userService.registerUser(registrationDTO);
    }
}