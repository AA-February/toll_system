package com.example.spring_into.service.impl;


import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.exception.RecordNotFoundException;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.repository.OwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTest {

    private static final String OWNER_EMAIL = "test@test.com";
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private OwnerConverter ownerConverter;
    @Mock
    private TollRepository tollRepository;
    @Mock
    private TollConverter tollConverter;
    @InjectMocks
    private TollService tollService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private OwnerServiceImpl ownerService;

    private Owner owner;
    private Set<TollPass> tollPass;
    @BeforeEach
    void setUp(){
        owner = new Owner();
        owner.setEmail(OWNER_EMAIL);
        owner.setFirstName("Ivan");
        owner.setLastName("Ivanov");
        owner.setPassword("ssdafasfasfasfas");
        tollPass = Set.of(new TollPass());

        owner.setTollPass(tollPass);
    }

    @Test
    void getTollsForOwner_success() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        Set<TollPass> ownersPass = ownerService.getTollsForOwner(1L);

        verify(ownerRepository,times(1)).findById(1L);

        assertNotNull(ownersPass);
        assertEquals(1,owner.getTollPass().size());
    }

    @Test
    void getTollsForOwner_OwnerNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, ()-> ownerService.getTollsForOwner(1L));

        assertEquals("Owner with id 1 not found.",exception.getMessage());
    }

    @Test
    void updateOwner_success() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(any(Owner.class));

       Owner testOwner = ownerService.updateOwner(buildOwnerRequest(),1L);

        verify(ownerRepository,times(1)).findById(1L);
        verify(ownerRepository,times(1)).save(any(Owner.class));

    }

    @Test
    void updateOwner_ownerNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());


        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,() -> ownerService.updateOwner(buildOwnerReques(),1L));
        assertEquals("Owner with id: 1 not exists",exception.getMessage());
        verify(ownerRepository,times(1)).findById(1L);
        verify(ownerRepository,times(0)).save(any(Owner.class));

    }

    private OwnerRequest buildOwnerReques(){
        OwnerRequest ownerRequest = new OwnerRequest();
        ownerRequest.setEmail("test@test.com");
        ownerRequest.setAddress("Bulgaria,Varna, Tsar Osvoboditel 122");
        ownerRequest.setPassword("123123123");
        return ownerRequest;
    }

    @Test
    void loginSuccessful(){
        when(ownerRepository.findByEmail(buildLoginRequest().getEmail())).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches(any(),any())).thenReturn(true);

        OwnerResponse ownerResponse = ownerService.login(buildLoginRequest());
        assertNotNull(ownerResponse);
        assertEquals(buildLoginRequest().getEmail(),ownerResponse.getEmail());

    }
    @Test
    void loginUnsuccessful(){
        when(ownerRepository.findByEmail(buildLoginRequest().getEmail())).thenReturn(Optional.empty());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,()->ownerService.login(buildLoginRequest()));
        verify(ownerRepository,times(1)).findByEmail(buildLoginRequest().getEmail());

    }

    private LoginRequest buildLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("1213123412");
        return loginRequest;
    }

    void findOwnerByID_success() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        OwnerResponse ownerResponse = ownerService.findOwnerById(1L);
        verify(ownerRepository, times(1)).findById(1L);
        assertEquals("Ivan", ownerResponse.getFirstName());
        assertEquals("Ivanov", ownerResponse.getLastName());
    }

    @Test
    void findOwnerById_OwnerNotFound() {

        when((ownerRepository.findById(anyLong()))).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> ownerService.findOwnerById(1L));
        assertEquals("Customer with id: 1 not found", exception.getMessage());
        verify(ownerRepository, times(1)).findById(1L);
    }


    @Test
    void checkValidity() {
        String regNumber = "A1234B";
        String country = "Bulgaria";
        TollPass tollPass1 = new TollPass();
        Instant expDate = Instant.now().plusSeconds(3600);
        tollPass1.setExpDate(expDate);
        when(tollRepository.findByRegNumberAndCountry(regNumber, country)).thenReturn(Optional.of(tollPass1));

        ValidityResponse response = tollService.checkValidity(regNumber, country);
        assertTrue(response.getValid());
    }


}