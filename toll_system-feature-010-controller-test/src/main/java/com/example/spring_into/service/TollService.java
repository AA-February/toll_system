package com.example.spring_into.service;

import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.dto.ValidityResponse;

public interface TollService {
    TollResponse addToll(TollRequest tollPass);

    ValidityResponse checkValidity(String regNumber, String country);

    void deleteToll(Long tollId);
}
