package com.example.spring_into.dto;

import lombok.Data;

@Data
public class ValidityResponse {
    private Boolean valid;
    private String expDate;
}
