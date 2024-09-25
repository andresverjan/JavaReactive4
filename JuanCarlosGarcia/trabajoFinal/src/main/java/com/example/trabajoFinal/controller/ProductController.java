package com.example.trabajoFinal.controller;

import com.example.trabajoFinal.model.Product;
import com.example.trabajoFinal.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product.toString()))
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage())));
    }

    @GetMapping("/name/{name}")
    public Mono<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productService.update(id, product)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable Integer id) {
        return productService.delete(id)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage())));
    }

    @PatchMapping("/{id}/stock")
    public Mono<ResponseEntity<Product>> modifyStock(@PathVariable Integer id, @RequestParam int stock) {
        return productService.modifyStock(id, stock)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null)));
    }
}