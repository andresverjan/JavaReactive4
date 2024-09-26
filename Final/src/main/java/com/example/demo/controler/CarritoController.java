package com.example.demo.controler;

import com.example.demo.model.Carrito;
import com.example.demo.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener el contenido del carrito de un usuario
    @GetMapping("/contents/{userId}")
    public Flux<Carrito> getCarritoContents(@PathVariable Long userId) {
        return carritoService.getCarritoContents(userId);
    }

    // Agregar un producto al carrito del usuario
    @PostMapping("/add/{userId}")
    public Mono<ResponseEntity<String>> addItemToCarrito(@PathVariable Long userId, @RequestBody Carrito item) {
        return carritoService.addItemToCarrito(userId, item)
                .map(message -> ResponseEntity.ok(message))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/remove/{id}")
    public Mono<Void> removeItemFromCarrito(@PathVariable Long id) {
        return carritoService.removeItemFromCarrito(id);
    }

    // Actualizar la cantidad de un producto en el carrito
    @PutMapping("/update/{id}")
    public Mono<Carrito> updateItemQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return carritoService.updateItemQuantity(id, quantity);
    }

    // Vaciar el carrito de un usuario
    @DeleteMapping("/clear/{userId}")
    public Mono<Void> clearCarrito(@PathVariable Long userId) {
        return carritoService.clearCarrito(userId);
    }

    // Calcular el total del carrito de un usuario
    @GetMapping("/total/{userId}")
    public Mono<Double> calculateTotal(@PathVariable Long userId) {
        return carritoService.calculateTotal(userId);
    }
}