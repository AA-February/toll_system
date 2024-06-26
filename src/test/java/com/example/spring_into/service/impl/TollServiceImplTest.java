package com.example.spring_into.service.impl;

import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.converter.TollConverter;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.dto.ValidityResponse;
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
import java.time.Instant;
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


    private TollRequest tollRequest ;
    private Owner owner;
    private TollPass tollPass;
    private TollPass tollPass1;

    @BeforeEach
    void setUp() {
        tollRequest = new TollRequest();
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
        when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(owner));
        when(tollConverter.toTollPass(any())).thenReturn(new TollPass());
        when(ownerConverter.toOwner(any(TollRequest.class))).thenReturn(new Owner());

        TollResponse tollResponse = tollService.addToll(tollRequest);

        verify(tollRepository,times(1)).save(any());
        verify(tollConverter, times(1)).toTollResponse(tollRepository.save(any(TollPass.class)));
    }

    @Test
    void addToll_ifOwnerNotExists() {
        when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(ownerRepository.save(any(Owner.class))).thenReturn(new Owner());
        when(tollConverter.toTollPass(any())).thenReturn(new TollPass());
        when(ownerConverter.toOwner(any(TollRequest.class))).thenReturn(new Owner());

        TollResponse tollResponse = tollService.addToll(tollRequest);

        verify(ownerRepository, times(1)).save(any());
        verify(tollConverter, times(1)).toTollResponse(any());
    }

    @Test
    void checkValidity_success() {
        String regNumber = "A1234B";
        String country = "Bulgaria";
        TollPass tollPass1 = new TollPass();
        Instant expDate = Instant.now().plusSeconds(3600);
        tollPass1.setExpDate(expDate);
        when(tollRepository.findByRegNumberAndCountry(regNumber, country)).thenReturn(Optional.of(tollPass1));

        ValidityResponse response = tollService.checkValidity(regNumber, country);
        assertTrue(response.getValid());
    }

    @Test
    void checkValidity_failure() {
        String regNumber = "A1234B";
        String country = "Bulgaria";
        TollPass tollPass1 = new TollPass();
        Instant expDate = Instant.now().minusSeconds(3600);
        tollPass1.setExpDate(expDate);
        when(tollRepository.findByRegNumberAndCountry(regNumber, country)).thenReturn(Optional.of(tollPass1));

        ValidityResponse response = tollService.checkValidity(regNumber, country);
        assertTrue(response.getValid());
    }


}