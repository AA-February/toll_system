package com.example.spring_into.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class TollResponse {
    private String startDate;
    private String expDate;
    @Email
    private String email;
    private String regNumber;
}
