package com.blitzstriker.goodreads.payload.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegisterDto {
    @NotEmpty(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Email(message = "Email is invalid")
    private String email;
}
