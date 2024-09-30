package com.programacion.reactiva.trabajo_final.exceptions;

import com.programacion.reactiva.trabajo_final.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e, ServerHttpRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, ServerHttpRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error en el servidor");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
