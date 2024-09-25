package com.example.shopping_cart.controllers;

import com.example.shopping_cart.model.Product;
import com.example.shopping_cart.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    //obtener todos los productos
    @GetMapping("/")
    public Flux<Product> getProducts(){return productService.getProducts();}
    //guardar nuevo producto
    @PostMapping
    public Mono<Product> create(@RequestBody Product p){return productService.createProduct(p);}
    //actualizar producto

    @PutMapping
    public Mono<String> update(@RequestBody Product p){
        return productService.updateProduct(p);
    }
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable Integer id){
        return productService.deleteProductById(id)
                .doOnSubscribe(subscription -> System.out.println("id a eliminar: "+id))
                .doOnError(e1->System.out.println("Error log: "+e1));

    }
    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Integer id){return productService.getProductById(id);}


    @GetMapping("/byname/{name}")
    public Flux<Product> getProductByName(@PathVariable String name){return productService.getProductByName(name);}


    @PostMapping("/alterStock/{id}")
    public Mono<Product> updateProductStock(@PathVariable Integer id, @RequestBody Integer stock){return productService.updateProductStock(id, stock);}
}
