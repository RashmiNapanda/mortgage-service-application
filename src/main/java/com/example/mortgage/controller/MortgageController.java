package com.example.mortgage.controller;

import com.example.mortgage.dto.MortgageCheckRequest;
import com.example.mortgage.dto.MortgageCheckResponse;
import com.example.mortgage.model.InterestRate;
import com.example.mortgage.service.MortgageService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MortgageController {

    private final MortgageService service;

    public MortgageController(MortgageService service) {
        this.service = service;
    }

    @Operation(summary = "Get current mortgage interest rates")
    @GetMapping("/interest-rates")
    @ResponseStatus(HttpStatus.OK)
    public List<InterestRate> getInterestRates() {
        return service.getInterestRates();
    }

    @Operation(summary = "Check mortgage feasibility and monthly cost")
    @PostMapping("/mortgage-check")
    @ResponseStatus(HttpStatus.CREATED)
    public MortgageCheckResponse check(
            @Valid @RequestBody MortgageCheckRequest request) {
        return service.checkMortgage(request);
    }
}
