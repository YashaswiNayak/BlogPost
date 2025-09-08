package com.yashaswi.BlogPost.controller;

import com.yashaswi.BlogPost.dto.LoginRequestDTO;
import com.yashaswi.BlogPost.dto.UserRegistrationDTO;
import com.yashaswi.BlogPost.dto.UserResponseDTO;
import com.yashaswi.BlogPost.service.JwtService;
import com.yashaswi.BlogPost.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserResponseDTO getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getProfile(userDetails.getUsername());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUserName(),
                        loginRequestDTO.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }

    @PutMapping("/profile")
    public UserResponseDTO updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserResponseDTO userResponseDTO) {
        return userService.updateProfile(userDetails.getUsername(), userResponseDTO);
    }
}