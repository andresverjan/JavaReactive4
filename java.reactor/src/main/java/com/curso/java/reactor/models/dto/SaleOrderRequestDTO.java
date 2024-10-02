package com.curso.java.reactor.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaleOrderRequestDTO {
    private List<ProductQuantityDTO> products;
    private Double shipment;
}
