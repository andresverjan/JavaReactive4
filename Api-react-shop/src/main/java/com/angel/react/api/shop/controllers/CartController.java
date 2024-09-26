package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.SupplierEntity;
import com.angel.react.api.shop.service.CartService;
import com.angel.react.api.shop.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/retrieve")
    public Flux<CartEntity> finAll(){
        return this.cartService.getAll();
    }

    @GetMapping("/client/{idCliente}")
    public Flux<CartEntity> finById(@PathVariable Long idCliente){
        return this.cartService.getByClient(idCliente);
    }

    @PostMapping()
    public Flux<CartEntity> create(@RequestBody List<CartEntity> cart){
        return this.cartService.create(cart);
    }

    @PutMapping()
    public Flux<CartEntity> update(@RequestBody List<CartEntity> cart){
        return this.cartService.update(cart);
    }

    @DeleteMapping("/item/{id}")
    public Mono<ResponseEntity<String>> deleteById(@PathVariable Long id){
        return this.cartService.deleteById(id)
                .doOnSubscribe(s -> log.info("Item of the cart deleted, id {}", id))
                .doOnError(e -> System.out.println("error"))
                .then(Mono.empty());
    }

    @DeleteMapping("/client/{id}")
    public Mono<ResponseEntity<String>> emtyByCient(@PathVariable Long id){
        return this.cartService.emptyByClient(id)
                .doOnSubscribe(s -> log.info("Empty cart of the client, id {}", id))
                .doOnError(e -> System.out.println("error"))
                .then(Mono.empty());
    }
}
