package com.example.demo.models;

import lombok.Data;

@Data
public class SaveResponse {
    private String description;
    private Shopping shopping;
    private Product product;


    public SaveResponse(String description,Shopping shopping, Product product) {
        this.description=description;
        this.shopping = shopping;
        this.product = product;

    }
}
