package com.programacion.reactiva.trabajo_final.controllers;

import com.programacion.reactiva.trabajo_final.model.OrdenVentaRequest;
import com.programacion.reactiva.trabajo_final.model.dto.OrdenVentaDTO;
import com.programacion.reactiva.trabajo_final.model.dto.VentaDTO;
import com.programacion.reactiva.trabajo_final.service.OrdenVentaService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/venta")
public class OrdenVentaController {
    private final OrdenVentaService ordenVentaService;

    public OrdenVentaController(OrdenVentaService ordenVentaService) {
        this.ordenVentaService = ordenVentaService;
    }

    @PostMapping
    public Mono<VentaDTO> registrarOrdenVenta(@RequestBody OrdenVentaRequest ordenVentaRequest){
       return ordenVentaService.crearOrdenVentaDirecta(ordenVentaRequest.getProductos(), ordenVentaRequest.getEnvio());
    }

    @GetMapping
    public Flux<OrdenVentaDTO> listarOrdenesVenta(){
        return ordenVentaService.listarOrdenesVenta();
    }

    @GetMapping("/{ordenVentaId}")
    public Mono<OrdenVentaDTO> obtenerOrdenVentaPorId(@PathVariable Long ordenVentaId){
        return ordenVentaService.obtenerOrdenVentaPorId(ordenVentaId);
    }

    @GetMapping("/producto/{productoId}")
    public Flux<OrdenVentaDTO> listarOrdenesVentaPorProducto(@PathVariable Long productoId){
        return ordenVentaService.listarOrdenesVentaPorProducto(productoId);
    }

    @PutMapping("/{ordenVentaId}")
    public Mono<OrdenVentaDTO> cambiarEstadoOrdenVenta(@PathVariable Long ordenVentaId, @RequestParam String estado){
        return ordenVentaService.cambiarEstadoOrdenVenta(ordenVentaId, estado);
    }
}
