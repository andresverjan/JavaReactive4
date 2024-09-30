package com.programacion.reactiva.actividad_final.model.dto;

import lombok.Data;

@Data
public class CartTotalDTO {
    private double subtotal;
    private double tax;
    private double shippingCost;
    private double total;

}
