package com.programacion.reactiva.trabajo_final.exceptions;


public class BusinessException  extends RuntimeException{
    private final int statusCode;
    private final String message;

    public BusinessException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
