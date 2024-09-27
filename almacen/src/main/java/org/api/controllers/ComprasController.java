package org.api.controllers;

import org.api.model.OrdenCompra;
import org.api.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/compras")
public class ComprasController {

     @Autowired
    private CompraService ordenCompraService;


    @PostMapping
    public Mono<OrdenCompra> crearOrdenCompra(@RequestBody OrdenCompra ordenCompra) {
        return ordenCompraService.crearOrdenCompra(ordenCompra);
    }

    @GetMapping
    public Flux<OrdenCompra> listarOrdenes() {
        return ordenCompraService.listarOrdenes();
    }
}