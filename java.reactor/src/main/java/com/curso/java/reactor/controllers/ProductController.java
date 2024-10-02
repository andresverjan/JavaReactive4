package com.curso.java.reactor.controllers;

import lombok.AllArgsConstructor;
import com.curso.java.reactor.models.Product;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.curso.java.reactor.services.ProductService;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public Flux<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> findProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    @GetMapping("/name/{name}")
    public Flux<Product> findProductByName(@PathVariable String name) {
        return productService.findProductByName(name);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.editProduct(id, product);
    }

    @PutMapping("/stock/{id}")
    public Mono<Product> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        return productService.updateStock(id, quantity);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}

