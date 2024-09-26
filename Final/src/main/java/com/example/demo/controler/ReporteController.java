package com.example.demo.controler;

import com.example.demo.model.Persona;
import com.example.demo.model.Producto;
import com.example.demo.model.Reporte;
import com.example.demo.service.ProductoService;
import com.example.demo.service.ReporteService;
import jakarta.validation.Valid;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/compras")
    public Mono<Reporte> generarReporteCompras(
            @RequestParam("fechaInicio") LocalDateTime fechaInicio,
            @RequestParam("fechaFin") LocalDateTime fechaFin) {
        return reporteService.generarReporteCompras(fechaInicio, fechaFin);
    }

    @GetMapping("/ventas")
    public Mono<Reporte> generarReporteVentas(
            @RequestParam("fechaInicio") LocalDateTime fechaInicio,
            @RequestParam("fechaFin") LocalDateTime fechaFin) {
        return reporteService.generarReporteVentas(fechaInicio, fechaFin);
    }

    @GetMapping("/top5")
    public Mono<List<Producto>> generarTop5ArticulosVendidos(
            @RequestParam("fechaInicio") LocalDateTime fechaInicio,
            @RequestParam("fechaFin") LocalDateTime fechaFin) {
        return reporteService.generarTop5ArticulosVendidos(fechaInicio, fechaFin);
    }
}