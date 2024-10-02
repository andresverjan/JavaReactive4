package com.curso.java.reactor.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class PurchaseOrderDTO {
    private Long purchaseId;
    private List<PurchaseOrderProductDTO> items;
    private String state;
    private double total;
}
