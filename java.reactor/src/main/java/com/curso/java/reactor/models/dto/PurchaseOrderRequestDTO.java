package com.curso.java.reactor.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderRequestDTO {
    private List<ProductQuantityDTO> products;
    private double total;
}
