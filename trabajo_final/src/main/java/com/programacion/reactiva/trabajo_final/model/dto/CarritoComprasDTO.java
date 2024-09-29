package com.programacion.reactiva.trabajo_final.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long carritoId;
    private List<CarritoProductoDTO> items;
}
