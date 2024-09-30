package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.CartSummaryEntity;
import com.angel.react.api.shop.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/retrieve")
    public Flux<CartEntity> findAll(){
        return this.cartService.getAll();
    }

    @GetMapping("/client/{idCliente}")
    public Flux<CartEntity> findById(@PathVariable Long idCliente){
        return this.cartService.getByClient(idCliente);
    }

    @GetMapping("/summary/client/{idCliente}")
    public Mono<CartSummaryEntity> findSummaryById(@PathVariable Long idCliente){
        return this.cartService.getSummaryByClient(idCliente);
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
        return this.cartService.deleteById(id);
    }

    @DeleteMapping("/client/{id}")
    public Mono<ResponseEntity<String>> emtyByCient(@PathVariable Long id){
        return this.cartService.emptyByClient(id);
    }
}
