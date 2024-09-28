package com.store.shopping.entities;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductRepository {
    Mono<Product> getProduct(Integer id);
    Flux<Product> getProducts();
    Mono<Product> getProductByName(String name);
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Integer id,Product product);
    Mono<String> deleteProduct(Integer id);
}
