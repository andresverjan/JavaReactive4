package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesOrderRespository extends ReactiveCrudRepository<SalesOrder, Integer> {
    Mono<SalesOrder> findByIdAndStatus(Integer id, String status);

    Flux<SalesOrder> findAllByClientId(Integer clientId);
}
