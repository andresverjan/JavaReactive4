package com.example.trabajoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TotalCompra {
    private List<CarritosCompras> items;
    private Double iva;
    private Double costoEnvio;
    private Double total;
}
