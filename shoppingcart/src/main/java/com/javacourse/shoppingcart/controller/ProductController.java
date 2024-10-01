package com.javacourse.shoppingcart.controller;

import com.javacourse.shoppingcart.model.Product;
import com.javacourse.shoppingcart.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService  productService;

    @GetMapping
    public Flux<Product> getProducts(){return productService.getProducts();}

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public Mono<Product> create(@RequestBody Product p){
        return productService.create(p);
    }

    @PutMapping
    public Mono<String> update(@RequestBody Product p){
        return productService.update(p);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProductById(@PathVariable Long id){
        return productService.deletePersonById(id)
                .doOnSubscribe(subscription -> System.out.println("id to be deleted: "+id))
                .doOnError(err -> System.out.println("Issues deleting the user with id: "+id))
                .then(Mono.empty());
    }

    @GetMapping("/name/{name}")
    public Mono<Product> getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @GetMapping("/name")
    public Mono<Product> getProductByName2(@RequestBody Product p){
        return productService.getProductByName(p.getName());
    }

    @PutMapping("/stock")
    public Mono<String> updateStock(@RequestBody Product p){
        return productService.update(p);
    }

}
