package com.example.spring_into.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class OwnerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    @Email
    private String email;
}
