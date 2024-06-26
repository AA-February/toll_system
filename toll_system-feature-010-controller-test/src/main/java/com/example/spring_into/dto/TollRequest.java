package com.example.spring_into.dto;

import com.example.spring_into.enums.Duration;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TollRequest {
    @NotNull(message = "Registration number cannot be null")
    @Size(min = 2 ,max = 10,message = "Registration number must be between 2 and 10 characters")
    private String regNumber;
    @NotNull(message = "Country cannot be null")
    @Size(min = 2 ,max = 150,message = "Country must be between 2 and 150 characters")
    private String country;
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 150,message = "First name must be between 2 and 150 characters")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 150,message = "Last name must be between 2 and 150 characters")
    private String lastName;
    @NotNull(message = "Address name cannot be null")
    @Size(min = 2, max = 200,message = "Address must be between 2 and 200 characters")
    private String address;
    @NotNull(message = "Duration can't be null")
    private Duration duration;
    @Email
    private String email;
}
