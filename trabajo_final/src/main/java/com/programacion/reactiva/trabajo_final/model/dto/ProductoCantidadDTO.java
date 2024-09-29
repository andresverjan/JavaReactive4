package com.programacion.reactiva.trabajo_final.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCantidadDTO {
    private Integer productoId;
    private Integer cantidad;
}
