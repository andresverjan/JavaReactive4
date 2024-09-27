package org.api.controllers;


import org.api.model.SalesOrder;
import org.api.model.SaleDto;
import org.api.service.SaleService;
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
public class SalesController {

    @Autowired
    private SaleService ordenService;


    @PostMapping
    public Mono<SalesOrder> crearOrden(@RequestBody SalesOrder orden) {
        return ordenService.crearOrden(orden);
    }

    @PutMapping("/{id}")
    public Mono<SalesOrder> editarOrden(@PathVariable Long id, @RequestBody SalesOrder orden) {
        return ordenService.editarOrden(id, orden);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> cancelarOrden(@PathVariable Long id) {
        return ordenService.cancelarOrden(id);
    }

    @GetMapping
    public Flux<SalesOrder> listarOrdenes() {
        return ordenService.listarOrdenes();
    }

    @GetMapping("/producto/{productoId}")
    public Flux<SaleDto> listarOrdenesPorProducto(@PathVariable Long productoId) {
        return ordenService.listarOrdenesPorProducto(productoId);
    }
}
