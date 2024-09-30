package org.example.actividadfinal.exceptions;

public class ProductException extends RuntimeException{

    public ProductException(String message) {
        super("Product: " + message);
    }
}
