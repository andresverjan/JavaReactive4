package com.programacion.reactiva.actividad_final.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private int cartId;
    private int userId;
    private List<ItemsDTO> items;
    private Double total;
}
