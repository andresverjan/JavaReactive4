package com.example.demo.models;

import lombok.Data;

@Data
public class ProductOrder {
    private int product_id;
    private int amount;
    private double price;
}
