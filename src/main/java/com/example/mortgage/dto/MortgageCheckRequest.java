package com.example.mortgage.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record MortgageCheckRequest(

        @NotNull(message = "Income must not be null")
        @Positive(message = "Income must be greater than zero")
        BigDecimal income,

        @NotNull(message = "Maturity period must not be null")
        @Min(value = 1, message = "Maturity period must be at least 1 year")
        Integer maturityPeriod,

        @NotNull(message = "Loan value must not be null")
        @Positive(message = "Loan value must be greater than zero")
        BigDecimal loanValue,

        @NotNull(message = "Home value must not be null")
        @Positive(message = "Home value must be greater than zero")
        BigDecimal homeValue
) {
}
