package com.example.mortgage.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        log.warn("Validation failed: {}", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }


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

            // Fallback for unexpected errors
            default -> {
                log.error("Unexpected error occurred", ex);
                yield buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal server error"
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
