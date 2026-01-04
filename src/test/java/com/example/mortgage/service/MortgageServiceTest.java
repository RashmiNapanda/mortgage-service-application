package com.example.mortgage.service;

import com.example.mortgage.dto.MortgageCheckRequest;
import com.example.mortgage.dto.MortgageCheckResponse;
import com.example.mortgage.exception.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MortgageServiceTest {

    private MortgageService mortgageService;

    @BeforeEach
    void setUp() {
        mortgageService = new MortgageService();
    }

    @Test
    @DisplayName("Should return feasible mortgage with monthly cost")
    void shouldReturnFeasibleMortgageWithMonthlyCost() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(50000),
                20,
                BigDecimal.valueOf(150000),
                BigDecimal.valueOf(200000)
        );

        MortgageCheckResponse response = mortgageService.checkMortgage(request);

        assertTrue(response.feasible());
        assertNotNull(response.monthlyCosts());


        assertNotEquals(BigDecimal.ZERO, response.monthlyCosts());
    }

    @Test
    @DisplayName("Should return not feasible mortgage when loan exceeds income limit")
    void shouldReturnNotFeasibleMortgageWhenLoanTooHigh() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(30000),
                20,
                BigDecimal.valueOf(200000),
                BigDecimal.valueOf(200000)
        );

        MortgageCheckResponse response = mortgageService.checkMortgage(request);

        assertFalse(response.feasible());
        assertNotNull(response.monthlyCosts());
    }

    @Test
    @DisplayName("Should throw exception when interest rate is not found")
    void shouldThrowExceptionWhenInterestRateNotFound() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(60000),
                15,   // unsupported maturity
                BigDecimal.valueOf(150000),
                BigDecimal.valueOf(200000)
        );

        assertThrows(
                NotFoundException.class,
                () -> mortgageService.checkMortgage(request)
        );
    }
}
