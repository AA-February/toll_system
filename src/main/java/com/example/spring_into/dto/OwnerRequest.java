package com.example.spring_into.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnerRequest {
    private String firstName;
    private String lastName;
    private String address;
    @Email
    private String email;
}
