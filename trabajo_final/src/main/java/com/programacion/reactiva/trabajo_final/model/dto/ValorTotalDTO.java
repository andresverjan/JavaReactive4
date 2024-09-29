package com.programacion.reactiva.trabajo_final.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ValorTotalDTO {
    private double valorTotal;
    private double subTotal;
    private double impuestos;
    private double costoEnvio;
}
