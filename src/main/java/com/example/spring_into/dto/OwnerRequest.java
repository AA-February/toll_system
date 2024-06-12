package com.example.spring_into.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnerRequest {
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 150,message = "First name must be between 2 and 150 characters")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 150,message = "Last name must be between 2 and 150 characters")
    private String lastName;
    @NotNull(message = "Address name cannot be null")
    @Size(min = 2, max = 200,message = "Address must be beween 2 and 200 characters")
    private String address;
    @Email
    private String email;
}
