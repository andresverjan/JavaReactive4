package com.programacion.reactiva.trabajo_final.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdenVentaRequestDTO {
    private List<ProductoCantidadDTO> productos;
    private Double envio;
}
