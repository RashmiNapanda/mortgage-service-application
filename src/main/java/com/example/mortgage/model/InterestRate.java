package com.example.mortgage.model;

import java.math.BigDecimal;
import java.time.Instant;

public record InterestRate(
        int maturityPeriod,
        BigDecimal interestRate,
        Instant lastUpdate
) {}
