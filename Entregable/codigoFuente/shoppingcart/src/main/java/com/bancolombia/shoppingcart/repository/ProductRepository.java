package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findByName(String name);
    //Optional<Mono<Product>> modifyStock(Long id, int stock);
}
