package com.example.spring_into.dto;

import com.example.spring_into.enums.Duration;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TollRequest {
    @NotNull
    private String regNumber;
    @NotNull
    private String country;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String address;
    @NotNull
    private Duration duration;
    @Email
    private String email;
}
