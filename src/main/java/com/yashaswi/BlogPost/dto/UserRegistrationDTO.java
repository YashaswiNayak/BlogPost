package com.yashaswi.BlogPost.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotEmpty(message = "Name needs to be filled")
    private String name;

    @NotEmpty(message = "Username needs to be filled")
    private String userName;

    @NotEmpty(message = "Password needs to be filled")
    @Size(min = 8,message = "Password must be 8 characters long")
    private String password;
}
