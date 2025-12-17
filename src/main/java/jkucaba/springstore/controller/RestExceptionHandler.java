package jkucaba.springstore.controller;

import jkucaba.springstore.exception.ErrorResponse;
import jkucaba.springstore.exception.StoreApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(StoreApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(StoreApiException ex) {

        ErrorResponse body = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                Instant.now()
        );

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "VALIDATION_ERROR",
                        ex.getFieldError().getDefaultMessage(),
                        Instant.now()
                ));
    }
}
