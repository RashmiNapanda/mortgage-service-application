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

    public MortgageCheckResponse checkMortgage(MortgageCheckRequest request) {
        log.info("Running mortgage check for maturityPeriod={}", request.maturityPeriod());

        boolean feasible = request.loanValue().compareTo(request.income().multiply(BigDecimal.valueOf(4))) <= 0
                && request.loanValue().compareTo(request.homeValue()) <= 0;

        if (!feasible) {
            log.warn("Mortgage not feasible for provided input");
        }

        InterestRate rate = interestRates.stream()
                .filter(r -> r.maturityPeriod() == request.maturityPeriod())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Interest rate not found"));

        /*BigDecimal monthlyRate = rate.interestRate()
                .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);*/

        //BigDecimal monthlyCost = request.loanValue().multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);

        BigDecimal monthlyCost = calculateMonthlyMortgage(
                request.loanValue(),
                rate.interestRate(),
                request.maturityPeriod()
        );

        log.info("Mortgage check completed. feasible={}", feasible);
        return new MortgageCheckResponse(feasible, monthlyCost);
    }

    private BigDecimal calculateMonthlyMortgage(
            BigDecimal loanValue,
            BigDecimal annualInterestRate,
            int maturityPeriodYears) {

        int months = maturityPeriodYears * 12;

        BigDecimal monthlyRate = annualInterestRate
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal factor = BigDecimal.ONE.add(monthlyRate)
                .pow(months, MathContext.DECIMAL128);

        return loanValue
                .multiply(monthlyRate)
                .multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}