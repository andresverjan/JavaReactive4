package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public Mono<ShoppingCart> addProduct(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.addProduct(shoppingCart);
    }
    @DeleteMapping("/{id}")
    public Mono<ShoppingCart> deleteProduct(@PathVariable Long id,@RequestBody int productId) {
        return shoppingCartService.deleteProduct(id,productId);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ShoppingCart>> updateProduct(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.updateShopping(id, shoppingCart)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public Flux<ShoppingCart> getProductsCart(@PathVariable Long id) {
        return shoppingCartService.getProductsCart(id);
    }
    @GetMapping("/total/{id}")
    public Mono<Double> calculateTotal(@PathVariable Long id) {
        return shoppingCartService.calculateTotal(id);
    }
    @DeleteMapping("/emptyCart/{id}")
    public Mono<Void> emptyCart(@PathVariable Long id) {
        System.out.println("eliminar");
        return shoppingCartService.emptyCart(id);
    }
}
