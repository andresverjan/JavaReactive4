package com.store.shopping.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {
    private Integer id;
    private String name;
    private Integer stock;
    private Double price;
}
