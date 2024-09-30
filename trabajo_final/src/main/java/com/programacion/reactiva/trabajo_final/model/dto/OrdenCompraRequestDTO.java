package com.programacion.reactiva.trabajo_final.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdenCompraRequestDTO {
    private List<ProductoCantidadDTO> productos;
    private double total;
}
