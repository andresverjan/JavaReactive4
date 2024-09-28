package com.programacion.reactiva.trabajo_final.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CarritoComprasDTO {
    private Long carritoId;
    private List<CarritoProductoDTO> items;
}
