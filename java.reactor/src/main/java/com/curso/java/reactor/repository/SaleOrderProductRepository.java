package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.SaleOrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SaleOrderProductRepository extends ReactiveCrudRepository<SaleOrderProduct, Long> {
    Flux<SaleOrderProduct> findBySaleOrderId(Long id);
    Flux<SaleOrderProduct> findByProductId(Long id);
}
