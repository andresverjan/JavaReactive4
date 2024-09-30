package com.programacion.reactiva.actividad_final.controllers;

import com.programacion.reactiva.actividad_final.model.Product;
import com.programacion.reactiva.actividad_final.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {


    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<Product> listProducts(){
        return productService.listProduct();
    }

    @GetMapping("/{id}")
    public Mono<Product> findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping("/name/{name}")
    public Flux<Product> findProductByName(@PathVariable String name){
        return productService.findProductByName(name);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product Product){
        return productService.createProduct(Product);
    }

    @PutMapping("/{id}")
    public Mono<Product> editProduct(@PathVariable Long id, @RequestBody Product Product){
        return productService.editProduct(id, Product);
    }

    @PutMapping("/stock/{id}")
    public Mono<Product> updateStock(@PathVariable Long id, @RequestParam int stock){
        return productService.updateStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }


}

