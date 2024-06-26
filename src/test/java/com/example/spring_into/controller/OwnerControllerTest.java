package com.example.spring_into.controller;


import static org.hamcrest.CoreMatchers.is;

import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
import com.example.spring_into.exception.RecordNotFoundException;
import com.example.spring_into.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OwnerController.class)
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OwnerService ownerService;
    @Autowired
    ObjectMapper objectMapper;
    OwnerRequest ownerRequest;
    OwnerResponse ownerResponse;

    @BeforeEach
    void setUp() {
        ownerRequest = new OwnerRequest();
        ownerRequest.setFirstName("Lyudmil");
        ownerRequest.setLastName("Filipov");
        ownerRequest.setEmail("john.doe@example.com");
        ownerRequest.setAddress("123 Main St");

        ownerResponse = new OwnerResponse();
        ownerResponse.setFirstName("Lyudmil");
        ownerResponse.setLastName("Filipov");
        ownerResponse.setEmail("john.doe@example.com");
        ownerResponse.setAddress("123 Main St");

    }

    @Test
    void addOwner_not_valid() throws Exception {
        when(ownerService.addOwner(any())).thenReturn(new OwnerResponse());

        mockMvc.perform(post("/api/v1/owner")
                        .content(objectMapper.writeValueAsString(new OwnerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addOwner_success() throws Exception {
        when(ownerService.addOwner(any())).thenReturn(ownerResponse);

        mockMvc.perform(post("/api/v1/owner")
                        .content(objectMapper.writeValueAsString(ownerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void getById_success() throws Exception {
        when(ownerService.findOwnerById(any())).thenReturn(ownerResponse);

        mockMvc.perform(get("/api/v1/owner/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.firstName", is("Lyudmil")));

    }

    @Test
    void getById_owner_not_found() throws Exception {
        when(ownerService.findOwnerById(any())).thenReturn(new RecordNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/owner/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}
