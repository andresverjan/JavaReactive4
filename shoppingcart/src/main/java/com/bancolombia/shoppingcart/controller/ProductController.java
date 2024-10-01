package com.bancolombia.shoppingcart.controller;

import com.bancolombia.shoppingcart.dto.ProductStockDTO;
import com.bancolombia.shoppingcart.entity.Product;
import com.bancolombia.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping(value = "/")
    public Mono<Product> getProductByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @PatchMapping("/{id}")
    public Mono<Product> modifyProductStock(@PathVariable Long id, @RequestBody ProductStockDTO product) {
        return productService.updateStock(id, product.getStock());
    }

    @GetMapping
    public Flux<Product> allProducts(){
        return productService.allProducts();
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
