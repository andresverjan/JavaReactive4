package com.example.trabajoFinal.controller;

import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.service.OrdenesVentasService;
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
@RequestMapping("/ordenesVentas")
public class OrdenesVentasController {
    private final OrdenesVentasService ordenesVentasService;

    public OrdenesVentasController(OrdenesVentasService ordenesVentasService) {
        this.ordenesVentasService = ordenesVentasService;
    }

    @PostMapping
    public Mono<ResponseEntity<OrdenesVentas>> createOrdenesVentas(@RequestBody OrdenesVentas ordenesVentas) {
        return ordenesVentasService.createOrdenesVentas(ordenesVentas)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public Flux<OrdenesVentas> listOrdenesVentas() {
        return ordenesVentasService.listOrdenesVentas();
    }

    @GetMapping("/product/{productId}")
    public Flux<OrdenesVentas> listSaleOrdenesVentas(@PathVariable int productId) {
        return ordenesVentasService.listOrdenesVentasByProduct(productId);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OrdenesVentas>> updateOrdenesVentas(@PathVariable int id,
                                                               @RequestBody OrdenesVentas ordenesVentas) {
        return ordenesVentasService.updateOrdenesVentas(id, ordenesVentas)
                .map(ResponseEntity::ok)
                .onErrorResume(IllegalStateException.class, e -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> cancelOrdenesVentas(@PathVariable int id) {
        return ordenesVentasService.cancelOrdenesVentas(id)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage())));
    }
}