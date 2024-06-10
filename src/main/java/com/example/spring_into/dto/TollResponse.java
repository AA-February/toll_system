package com.example.spring_into.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TollResponse {
    private String startDate;
    private String expDate;
    private String email;
    private String regNumber;
}
