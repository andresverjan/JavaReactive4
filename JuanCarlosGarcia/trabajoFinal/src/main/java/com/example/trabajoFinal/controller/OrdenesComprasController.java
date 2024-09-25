package com.example.trabajoFinal.controller;


import com.example.trabajoFinal.model.OrdenesCompra;
import com.example.trabajoFinal.service.OrdenesComprasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/ordenesCompra")
public class OrdenesComprasController {
    private final OrdenesComprasService ordenesComprasService;

    public OrdenesComprasController(OrdenesComprasService ordenesComprasService) {
        this.ordenesComprasService = ordenesComprasService;
    }

    @PostMapping
    public Mono<ResponseEntity<OrdenesCompra>> createOrdenCompra(@RequestBody OrdenesCompra ordenesCompra) {
        return ordenesComprasService.createOrder(ordenesCompra)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OrdenesCompra>> updateOrdenCompra(@PathVariable int id, @RequestBody OrdenesCompra ordenesCompra) {
        return ordenesComprasService.updateOrdenCompra(id, ordenesCompra)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> cancelOrdenCompra(@PathVariable int id) {
        return ordenesComprasService.cancelOrdenCompra(id)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage())));
    }

    @GetMapping
    public Flux<OrdenesCompra> listOrdenCompra() {
        return ordenesComprasService.listOrdenCompra();
    }
}