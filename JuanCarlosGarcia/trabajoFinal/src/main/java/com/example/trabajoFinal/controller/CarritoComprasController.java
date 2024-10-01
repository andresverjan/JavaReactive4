package com.example.trabajoFinal.controller;

import com.example.trabajoFinal.model.CarritosCompras;
import com.example.trabajoFinal.model.TotalCompra;
import com.example.trabajoFinal.service.CarritoComprasService;
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
@RequestMapping("/carrito")
public class CarritoComprasController {
    private final CarritoComprasService carritoComprasService;

    public CarritoComprasController(CarritoComprasService carritoComprasService) {
        this.carritoComprasService = carritoComprasService;
    }

    @PostMapping
    public Mono<CarritosCompras> addProductToCart(@RequestBody CarritosCompras carritoCompras) {
        return carritoComprasService.addProductToCart(carritoCompras);
    }

    @DeleteMapping("/remove/{productId}/{personaId}")
    public Mono<ResponseEntity<String>> removeProductFromCart(@PathVariable Integer productId, @PathVariable Integer personaId) {
        return carritoComprasService.removeProductFromCart(productId, personaId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<CarritosCompras> updateProductQuantityInCart(@RequestBody CarritosCompras carritoCompras) {
        return carritoComprasService.updateProductToCart(carritoCompras);
    }


    @GetMapping("/items/{personaId}")
    public Flux<CarritosCompras> getCartItems(@PathVariable Integer personaId) {
        return carritoComprasService.getCartItems(personaId);
    }

    @DeleteMapping("/empty/{personaId}")
    public Mono<String> emptyCart(@PathVariable Integer personaId) {
        return carritoComprasService.emptyCart(personaId);
    }
    @GetMapping("/calculateTotal/{personaId}")
    public Mono<ResponseEntity<TotalCompra>> calculateTotal(@PathVariable Integer personaId, @RequestBody TotalCompra totalCompra) {
        return carritoComprasService.calculateTotal(personaId, totalCompra)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/orden/{personaId}")
    public Flux<String> createOrdenFromCart(@PathVariable Integer personaId) {
        return carritoComprasService.createOrdenFromCart(personaId);
    }
}
