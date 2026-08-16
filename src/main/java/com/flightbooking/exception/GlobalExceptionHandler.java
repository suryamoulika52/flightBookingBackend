package com.flightbooking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(
            Exception ex) {

        ex.printStackTrace();

        Map<String, String> error = new HashMap<>();

        Throwable cause = ex;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        String message = cause.getMessage();

        if (message == null || message.isBlank()) {
            message = ex.getMessage();
        }

        error.put("message", message);

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
}