package com.example.spring_into.controller;

import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.enums.Duration;
import com.example.spring_into.service.OwnerService;
import com.example.spring_into.service.TollService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TollController.class)
@ExtendWith(MockitoExtension.class)
public class TollControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TollService tollService;
    @MockBean
    OwnerService ownerService;
    @Autowired
    ObjectMapper objectMapper;

    TollResponse tollResponse;
    TollRequest tollRequest;

    @BeforeEach
    void setUp() {
        tollRequest = new TollRequest();
        tollRequest.setFirstName("John");
        tollRequest.setLastName("Doe");
        tollRequest.setEmail("john.doe@example.com");
        tollRequest.setAddress("123 Main St");
        tollRequest.setRegNumber("12345");
        tollRequest.setDuration(Duration.WEEK);
        tollRequest.setCountry("Bulgaria");

        tollResponse = new TollResponse();
        tollResponse.setStartDate("12");
        tollResponse.setExpDate("13");
        tollResponse.setRegNumber("12345");
        tollResponse.setEmail("john.doe@example.com");

    }


@Test
void addToll_success() throws Exception {
    when(tollService.addToll(any())).thenReturn(tollResponse);

    mockMvc.perform(post("/api/v1/toll")
                    .content(objectMapper.writeValueAsString(tollRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName",is("John")));
}
}
