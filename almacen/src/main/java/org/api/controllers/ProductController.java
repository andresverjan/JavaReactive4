package org.api.controllers;

import org.api.model.Product;
import org.api.service.ProductService;
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
@RequestMapping("/api/productos")
public class ProductController {

    private static final String NOT_FOUND_MESSAGE = "El producto no existe";

    @Autowired
    private ProductService service;

    @GetMapping("/{id}")
     public Mono<Product> getProductById(@PathVariable Long id) {
        return service.getProductById(id)
                .switchIfEmpty(Mono.error(new NullPointerException(NOT_FOUND_MESSAGE)));
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/nombre/{name}")
    public Mono<Product> getProductByName(@PathVariable String name) {
        return service.getProductByName(name)
                .switchIfEmpty(Mono.error(new NullPointerException(NOT_FOUND_MESSAGE)));
    }

    @PostMapping("/crear")
    public Mono<Product> createProduct(@RequestBody Product p) {
        return service.addProduct(p);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable Long id, @RequestBody Product producto) {
        return service.getProductById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(producto.getName());
                    existingProduct.setDescription(producto.getDescription());
                    existingProduct.setPrice(producto.getPrice());
                    return service.addProduct(existingProduct);
                })
                .switchIfEmpty(Mono.error(new NullPointerException(NOT_FOUND_MESSAGE)));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }


}
