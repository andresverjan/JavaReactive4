package com.angel.react.api.shop.controllers;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.model.ProductEntity;
import com.angel.react.api.shop.service.PersonService;
import com.angel.react.api.shop.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/retrieve")
    public Flux<ProductEntity> finAll(){
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ProductEntity> finById(@PathVariable Long id){
        return this.productService.getById(id);
    }

    @PostMapping()
    public Mono<ProductEntity> create(@RequestBody ProductEntity product){
        return this.productService.create(product);
    }

    @PutMapping()
    public Mono<ProductEntity> update(@RequestBody ProductEntity product){
        return this.productService.update(product);
    }

    @DeleteMapping("/{id}")
    public Mono<PersonEntity> deleteById(@PathVariable Long id){
        return this.productService.deleteById(id)
                .doOnSubscribe(s -> System.out.println(" id eliminada"))
                .doOnError(e -> System.out.println("error"))
                .then(Mono.empty());
    }
}
