package com.example.shopping_cart.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TopProductReport {
    private Integer productId;
    private Integer totalQuantitySold;
    public TopProductReport(Integer productId, Integer totalQuantitySold) {
        this.productId = productId;
        this.totalQuantitySold = totalQuantitySold;
    }
}
