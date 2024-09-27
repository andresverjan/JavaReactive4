package com.curso_java.tienda.controllers;

import com.curso_java.tienda.entities.CarritoCompra;
import com.curso_java.tienda.services.CarritoComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carritos")
public class CarritoComprasController {

    @Autowired
    private CarritoComprasService carritoComprasService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CarritoCompra> createCarrito(@RequestBody CarritoCompra carrito) {
        return carritoComprasService.saveCarrito(carrito);
    }

    @GetMapping
    public Flux<CarritoCompra> getAllCarritos() {
        return carritoComprasService.getAllCarritos();
    }

    @GetMapping("/{id}")
    public Mono<CarritoCompra> getCarritoById(@PathVariable String id) {
        return carritoComprasService.getCarritoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCarrito(@PathVariable String id) {
        return carritoComprasService.deleteCarrito(id);
    }
}

