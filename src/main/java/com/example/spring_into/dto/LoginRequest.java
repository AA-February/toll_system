package com.example.spring_into.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
