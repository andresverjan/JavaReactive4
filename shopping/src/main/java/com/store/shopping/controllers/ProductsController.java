package com.store.shopping.controllers;

import com.store.shopping.entities.Product;
import com.store.shopping.useCase.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }
    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    @GetMapping
    public Flux<Product> getProducts() {
        return productService.getProducts();
    }
    @PutMapping("/stock/{id}")
    public Mono<Product> updateStock(@PathVariable Integer id,@RequestParam(name = "unities") Integer stock){
        return productService.updateStock(id, stock);
    }
    @GetMapping("/search")
    public Mono<Product> getProductByeName(@RequestParam(name = "name") String name) {
        return productService.getProductByName(name);
    }
    @DeleteMapping("/{id}")
    public Mono<String> deleteProduct(@PathVariable Integer id){
        return  productService.deleteProduct(id);
    }

}
