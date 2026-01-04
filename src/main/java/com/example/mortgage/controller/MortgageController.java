package com.example.mortgage.controller;

import com.example.mortgage.dto.MortgageCheckRequest;
import com.example.mortgage.dto.MortgageCheckResponse;
import com.example.mortgage.model.InterestRate;
import com.example.mortgage.service.MortgageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MortgageController {

    private final MortgageService service;

    public MortgageController(MortgageService service) {
        this.service = service;
    }

    @GetMapping("/interest-rates")
    public List<InterestRate> getInterestRates() {
        return service.getInterestRates();
    }

    @PostMapping("/mortgage-check")
    public MortgageCheckResponse check(@Valid @RequestBody MortgageCheckRequest request) {
        return service.checkMortgage(request);
    }
}