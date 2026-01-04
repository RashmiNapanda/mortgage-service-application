package com.example.mortgage.service;

import com.example.mortgage.dto.MortgageCheckRequest;
import com.example.mortgage.dto.MortgageCheckResponse;
import com.example.mortgage.exception.NotFoundException;
import com.example.mortgage.model.InterestRate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;


@Service
public class MortgageService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(MortgageService.class);

    private final List<InterestRate> interestRates = List.of(
            new InterestRate(10, BigDecimal.valueOf(3.0), Instant.now()),
            new InterestRate(20, BigDecimal.valueOf(3.5), Instant.now()),
            new InterestRate(30, BigDecimal.valueOf(4.0), Instant.now())
    );

    public List<InterestRate> getInterestRates() {
        log.info("Fetching interest rates");
        return interestRates;
    }


}