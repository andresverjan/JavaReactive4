package com.example.demo.controller;

import com.example.demo.models.Person;
import com.example.demo.models.Product;
import com.example.demo.service.PersonService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //creacion
    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.createProduct(product)
                .map(savedPerson -> ResponseEntity.status(HttpStatus.CREATED).body(savedPerson));
    }

    // listado
    @GetMapping
    public Flux<Product> getAllProduct() {
        return productService.getAllProducts();
    }
    //buscarById
    @GetMapping("findId/{id}")
    public Mono<ResponseEntity<Product>> getPersonById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //buscarName
    @GetMapping("findName/{name}")
    public Mono<ResponseEntity<Product>> getPersonByName(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    //edicion
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //modificarStock
    @PutMapping("/stock/{id}")
    public Mono<ResponseEntity<Product>> updateStockProduct(@PathVariable Long id, @RequestBody int stock) {
        return productService.updateProductStock(id, stock)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //Eliminado
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
