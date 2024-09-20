package com.example.demo.models;

import lombok.Data;

@Data
public class ResponseTotal {
    double total_cart;
    public ResponseTotal(double total_cart) {
        this.total_cart=total_cart;

    }
}
