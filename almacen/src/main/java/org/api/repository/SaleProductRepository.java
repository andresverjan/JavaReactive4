package org.api.repository;

import org.api.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SaleProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findByName(String name);
}
