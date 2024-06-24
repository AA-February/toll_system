package com.example.spring_into.service.impl;

import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.converter.TollConverter;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.dto.ValidityResponse;
import com.example.spring_into.exception.RecordNotFoundException;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.repository.TollRepository;
import com.example.spring_into.service.TollService;
import com.example.spring_into.util.Helper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class TollServiceImpl implements TollService {

    private final TollRepository tollRepository;
    private final TollConverter tollConverter;
    private final OwnerRepository ownerRepository;
    private final OwnerConverter ownerConveter;

    @Autowired
    public TollServiceImpl(TollRepository tollRepository, TollConverter tollConverter, OwnerRepository ownerRepository, OwnerConverter ownerConveter) {
        this.tollRepository = tollRepository;
        this.tollConverter = tollConverter;
        this.ownerRepository = ownerRepository;
        this.ownerConveter = ownerConveter;
    }

    @Transactional
    @Override
    public TollResponse addToll(TollRequest tollRequest) {
        Optional<Owner> owner = ownerRepository.findByEmail(tollRequest.getEmail());
        Owner existingOwner;

        if (owner.isEmpty()) {
            log.info("Going to create new owner with identifier: " + tollRequest.getEmail());
            existingOwner = ownerRepository.save(ownerConveter.toOwner(tollRequest));

        } else {
            log.info("Going to use existing owner with identifier: " + tollRequest.getEmail());
            existingOwner = owner.get();
        }

        TollPass tollPass = tollConverter.toTollPass(tollRequest);
        tollPass.setOwner(existingOwner);
        return tollConverter.toTollResponse(tollRepository.save(tollPass));
    }

    @Override
    public ValidityResponse checkValidity(String regNumber, String country) {
        Optional<TollPass> tollPass = tollRepository.findByRegNumberAndCountry(regNumber, country);


        ValidityResponse validityResponse = new ValidityResponse();
        if (!tollPass.isEmpty()) {
            Instant expDate = tollPass.get().getExpDate();
            validityResponse.setValid(isValid(expDate));

            validityResponse.setExpDate(Helper.formatDate(expDate));
            return validityResponse;
        } else {
            throw new RecordNotFoundException(String.format("Toll not found for registration number: %s," +
                    " and country: %s",regNumber,country));
        }

    }

    private boolean isValid(Instant expDate) {
        return expDate.isAfter(Instant.now());
    }

    @Override
    public void deleteToll(Long tollId) {
        log.info("Going to delete toll for id: " + tollId);
        tollRepository.deleteById(tollId);
    }
}
