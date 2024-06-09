package com.example.spring_into.service.impl;

import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.converter.TollConverter;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.repository.TollRepository;
import com.example.spring_into.service.TollService;
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
    public TollPass addToll(TollRequest tollRequest) throws Exception {
        Optional<Owner> owner = ownerRepository.findByEmail(tollRequest.getEmail());
        Owner existingOwner;

        if(owner.isEmpty()){
            log.info("Going to create new owner with identifier: "+ tollRequest.getEmail());
            existingOwner = ownerRepository.save(ownerConveter.toOwner(tollRequest));

        }else {
            log.info("Going to use existing owner with identifier: "+ tollRequest.getEmail());
            existingOwner = owner.get();
        }

        TollPass tollPass = tollConverter.toTollPass(tollRequest);
        tollPass.setOwner(existingOwner);
        tollRepository.save(tollPass);
        return null;
    }

    @Override
    public boolean checkValidity(String regNumber, String country) {
        Optional<TollPass> tollPass = tollRepository.findByRegNumberAndCountry(regNumber,country);
        //sendFine()
        if(!tollPass.isEmpty()){
            return !tollPass.get().getExpDate().isBefore(Instant.now());
        } else{
            return false;
        }

    }

    @Override
    public void deleteToll(Long tollId) {

        log.info("Going to delete toll for id: " + tollId);
        tollRepository.deleteById(tollId);
    }
}
