package com.example.spring_into.service.impl;

import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.converter.TollConverter;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.enums.Duration;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.repository.TollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TollServiceImplTest {

    @Mock
    private TollRepository tollRepository;
    @Mock
    private TollConverter tollConverter;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private OwnerConverter ownerConverter;

    @InjectMocks
    private TollServiceImpl tollService;


    private TollRequest tollRequest;
    private Owner owner;
    private TollPass tollPass;

    @BeforeEach
    void setUp() {
        tollRequest.setFirstName("lyudmil");
        tollRequest.setLastName("chertsov");
        tollRequest.setRegNumber("SH4727TT");
        tollRequest.setEmail("lyudmil.chertsov@abv.bg");
        tollRequest.setDuration(Duration.WEEK);
        tollRequest.setAddress("Bulgaria, Dobrich, ul. Kableshkov 27, Ap. 55");
        tollRequest.setCountry("Bulgaria");
        tollConverter.toTollPass(tollRequest);
    }

    @Test
    void addToll_ifOwnerExists() {
        when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
        when(ownerRepository.save(ownerConverter.toOwner(tollRequest)));
        TollResponse tollResponse = tollService.addToll(tollRequest);

        verify(ownerRepository, times(0)).save(ownerConverter.toOwner(tollRequest));

        verify(tollConverter, times(1)).toTollResponse(tollRepository.save(any(TollPass.class)));
    }

    @Test
    void addToll_ifOwnerNotExists() {
        when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));

        verify(ownerRepository, times(1)).save(ownerConverter.toOwner(tollRequest));

        verify(tollConverter, times(1)).toTollResponse(tollRepository.save(any(TollPass.class)));
    }


}