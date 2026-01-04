package com.example.mortgage.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleExceptions(Exception ex) {

        return switch (ex) {

            // Business exception
            case NotFoundException nfe -> {
                log.error("Business exception: {}", nfe.getMessage());
                yield buildErrorResponse(
                        HttpStatus.NOT_FOUND,
                        nfe.getMessage()
                );
            }

        };
    }

    private ProblemDetail buildErrorResponse(HttpStatus status, String detail) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(status, detail);

        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
