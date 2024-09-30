package com.programacion.reactiva.actividad_final.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyDTO {
    private int cartId;
    private int orderId;
    private int userId;
    private int productId;
    private int quantity;
}
