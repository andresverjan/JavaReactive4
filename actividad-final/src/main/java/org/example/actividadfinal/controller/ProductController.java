package org.example.actividadfinal.controller;


import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.Product;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;


    @GetMapping
    public Mono<ResponseData> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/findById/{id}")
    public Mono<ResponseData> getProductById(@PathVariable String id) {
        return productService.findById(Long.parseLong(id));
    }


    @DeleteMapping(value = "/{id}")
    public Mono<ResponseData> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(Long.parseLong(id));
    }

    @GetMapping(value = "/findByName/{name}")
    public Mono<ResponseData> getProductsByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @PostMapping
    public Mono<ResponseData> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PostMapping(value = "/update")
    public Mono<ResponseData> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PutMapping(value = "/updateStock")
    public Mono<ResponseData> updateStock(@RequestBody Map<String, Long> mapStock) {
        return productService.updateStock(mapStock);
    }

}
