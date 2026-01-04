package com.example.mortgage.controller;

import com.example.mortgage.dto.MortgageCheckRequest;
import com.example.mortgage.dto.MortgageCheckResponse;
import com.example.mortgage.service.MortgageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MortgageController.class)
class MortgageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MortgageService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return feasible mortgage with monthly cost")
    void shouldReturnMortgageCheckResponse() throws Exception {
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(40000),
                20,
                BigDecimal.valueOf(150000),
                BigDecimal.valueOf(200000)
        );

        MortgageCheckResponse response =
                new MortgageCheckResponse(true, BigDecimal.valueOf(437.50));

        Mockito.when(service.checkMortgage(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyCosts").value(437.50));

        Mockito.verify(service).checkMortgage(Mockito.any());
    }

    @Test
    @DisplayName("Should return 400 Bad Request for invalid mortgage request")
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        MortgageCheckRequest invalidRequest = new MortgageCheckRequest(
                BigDecimal.ZERO,
                0,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );

        mockMvc.perform(post("/api/v1/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
