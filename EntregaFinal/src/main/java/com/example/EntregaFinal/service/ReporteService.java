package com.example.EntregaFinal.service;

import com.example.EntregaFinal.model.Orden;
import com.example.EntregaFinal.model.OrdenProducto;
import com.example.EntregaFinal.repository.OrdenProductoRepository;
import com.example.EntregaFinal.repository.OrdenRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
public class ReporteService {

    private final OrdenRepository ordenRepository;
    private final OrdenProductoRepository ordenProductoRepository;

    public ReporteService(OrdenRepository ordenRepository, OrdenProductoRepository ordenProductoRepository) {
        this.ordenRepository = ordenRepository;
        this.ordenProductoRepository = ordenProductoRepository;
    }

    // Reporte de todas las órdenes en un intervalo de tiempo
    public Flux<Orden> obtenerOrdenesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    // Reporte de la cantidad total de cada producto vendido en un intervalo de tiempo
    public Flux<OrdenProducto> obtenerVentasPorProductoEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenRepository.findByFechaBetween(fechaInicio, fechaFin)
                .flatMap(orden -> ordenProductoRepository.findByOrdenId(orden.getId()));
    }

    // Reporte del top 5 productos más vendidos en un intervalo de tiempo
    public Flux<OrdenProducto> obtenerTopProductosVendidos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return obtenerVentasPorProductoEntreFechas(fechaInicio, fechaFin)
                .groupBy(OrdenProducto::getProductoId)
                .flatMap(groupedFlux -> groupedFlux.reduce((ordenProducto1, ordenProducto2) -> {
                    ordenProducto1.setCantidad(ordenProducto1.getCantidad() + ordenProducto2.getCantidad());
                    return ordenProducto1;
                }))
                .sort((o1, o2) -> Integer.compare(o2.getCantidad(), o1.getCantidad()))  // Ordenar de mayor a menor
                .take(5);
    }

}
