package com.programacion.reactiva.actividad_final.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDTO {
    private int quantity;
    private ProductDTO product;
    private double subtotal;
}
