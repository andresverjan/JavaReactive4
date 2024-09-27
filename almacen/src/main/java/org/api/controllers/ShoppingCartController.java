package org.api.controllers;

import org.api.model.Product;
import org.api.model.ShoppingCart;
import org.api.service.ProductService;
import org.api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/carro")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @Autowired
    private ProductService productService;


    @PostMapping("/{userId}/agregar")
    public Mono<Product> agregarAlCarrito(@PathVariable String userId, @RequestParam Long productoId, @RequestParam int cantidad) {
        return service.getProductById(userId, productoId, cantidad);
    }

    @GetMapping("/{userId}")
    public Mono<ShoppingCart> obtenerContenidoCarrito(@PathVariable String userId) {
        return service.obtenerContenidoCarrito(userId)
                .switchIfEmpty(Mono.error(new NullPointerException("El carrito no existe")));
    }

    @DeleteMapping("/{userId}/eliminar/{productoId}")
    public Mono<ShoppingCart> eliminarDelCarrito(@PathVariable String userId, @PathVariable Long productoId) {
        return service.eliminarDelCarrito(userId, productoId);
    }

    @PutMapping("/{userId}/actualizar/{productoId}")
    public Mono<Product> actualizarCantidad(@PathVariable String userId, @PathVariable Long productoId, @RequestParam int cantidad) {
        return service.actualizarCantidad(userId, productoId, cantidad);
    }

    @DeleteMapping("/{userId}/vaciar")
    public Mono<ShoppingCart> vaciarCarrito(@PathVariable String userId) {
        return service.vaciarCarrito(userId);
    }


}
