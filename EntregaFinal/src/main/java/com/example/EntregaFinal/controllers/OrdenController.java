package com.example.EntregaFinal.controllers;


import com.example.EntregaFinal.model.Orden;
import com.example.EntregaFinal.model.OrdenProducto;
import com.example.EntregaFinal.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")

public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Crear una nueva orden
    @PostMapping
    public Mono<Orden> crearOrden(@RequestBody Map<String, String> request) {
        String cliente = request.get("cliente");
        return ordenService.crearOrden(cliente);
    }
    // Agregar un producto a una orden
    @PostMapping("/{ordenId}/agregar-producto")
    public Mono<OrdenProducto> agregarProductoALaOrden(
            @PathVariable Long ordenId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        return ordenService.agregarProductoALaOrden(ordenId, productoId, cantidad);
    }

    // Obtener los productos de una orden
    @GetMapping("/{ordenId}/productos")
    public Flux<OrdenProducto> obtenerProductosDeLaOrden(@PathVariable Long ordenId) {
        return ordenService.obtenerProductosDeLaOrden(ordenId);
    }
}
