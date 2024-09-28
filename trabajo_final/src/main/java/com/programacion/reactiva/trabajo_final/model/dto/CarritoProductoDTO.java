package com.programacion.reactiva.trabajo_final.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programacion.reactiva.trabajo_final.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CarritoProductoDTO {
    private int cantidad;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long carritoId;
    private ProductoDTO producto;
}