package com.programacion.reactiva.trabajo_final.model;

import com.programacion.reactiva.trabajo_final.model.dto.ProductoCantidadDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrdenVentaRequest {
    private List<ProductoCantidadDTO> productos;
    private Double envio;
}
