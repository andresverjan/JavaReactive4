package org.api.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseDto {

    private Long id;
    private String estado;
    private LocalDateTime createdAt;
    private List<PurchaseProduct> productos;

}