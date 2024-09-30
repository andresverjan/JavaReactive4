package com.programacion.reactiva.trabajo_final.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class OrdenCompraDTO {
    private Long compraId;
    private List<OrdenCompraProductoDTO> items;
    private String estado;
    private double total;
}
