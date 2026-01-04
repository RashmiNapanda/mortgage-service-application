package com.example.mortgage.dto;

import java.math.BigDecimal;

public record MortgageCheckResponse(
        boolean feasible,
        BigDecimal monthlyCosts
) {}
