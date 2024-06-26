package com.example.spring_into.controller;

import static org.hamcrest.CoreMatchers.is;

import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
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
import static org.mockito.Mockito.when;
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
    private OwnerRequest ownerRequest;
    private OwnerResponse ownerResponse;

    @BeforeEach
    void setUp() {
        ownerRequest = new OwnerRequest();
        ownerRequest.setFirstName("John");
        ownerRequest.setLastName("Doe");
        ownerRequest.setEmail("john.doe@example.com");
        ownerRequest.setAddress("123 Main St");

        ownerResponse = new OwnerResponse();
        ownerResponse.setFirstName("John");
        ownerResponse.setLastName("Doe");
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
        when(ownerService.addOwner(ownerRequest)).thenReturn(ownerResponse);

        mockMvc.perform(post("/api/v1/owner")
                        .content(objectMapper.writeValueAsString(ownerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",is("John")));
    }
}
