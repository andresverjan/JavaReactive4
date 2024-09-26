package com.angel.react.api.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public Mono<Map<String, Object>> handleNotFoundException(NotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage() != null ? ex.getMessage() : "Error");
        response.put("path", ex.getLocalizedMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        // Crear un mapa para la respuesta personalizada
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getReason() != null ? ex.getReason() : "Error");
        response.put("status", ex.getDetailMessageCode());
        response.put("timestamp", LocalDateTime.now());

        return Mono.just(response);
    }
}
