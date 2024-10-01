package com.example.EntregaFinal.controllers;

import com.example.EntregaFinal.model.Orden;
import com.example.EntregaFinal.model.OrdenProducto;
import com.example.EntregaFinal.service.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {


    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Obtener todas las órdenes en un intervalo de tiempo
    @GetMapping("/ordenes")
    public Flux<Orden> obtenerOrdenesEntreFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fin = LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_DATE_TIME);
        return reporteService.obtenerOrdenesEntreFechas(inicio, fin);
    }

    // Obtener ventas de productos en un intervalo de tiempo
    @GetMapping("/ventas")
    public Flux<OrdenProducto> obtenerVentasPorProductoEntreFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fin = LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_DATE_TIME);
        return reporteService.obtenerVentasPorProductoEntreFechas(inicio, fin);
    }

    // Obtener el top 5 productos más vendidos en un intervalo de tiempo
    @GetMapping("/top-productos")
    public Flux<OrdenProducto> obtenerTopProductosVendidos(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fin = LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_DATE_TIME);
        return reporteService.obtenerTopProductosVendidos(inicio, fin);
    }
}
