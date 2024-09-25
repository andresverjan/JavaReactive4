package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByName(String name);
}
