package org.api.controllers;


import org.api.model.OrdenVenta;
import org.api.model.VentaDto;
import org.api.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    @Autowired
    private VentaService ordenService;


    @PostMapping
    public Mono<OrdenVenta> crearOrden(@RequestBody OrdenVenta orden) {
        return ordenService.crearOrden(orden);
    }

    @PutMapping("/{id}")
    public Mono<OrdenVenta> editarOrden(@PathVariable Long id, @RequestBody OrdenVenta orden) {
        return ordenService.editarOrden(id, orden);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> cancelarOrden(@PathVariable Long id) {
        return ordenService.cancelarOrden(id);
    }

    @GetMapping
    public Flux<OrdenVenta> listarOrdenes() {
        return ordenService.listarOrdenes();
    }

    @GetMapping("/producto/{productoId}")
    public Flux<VentaDto> listarOrdenesPorProducto(@PathVariable Long productoId) {
        return ordenService.listarOrdenesPorProducto(productoId);
    }
}
