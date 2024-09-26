package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.CartEntity;
import com.angel.react.api.shop.model.PurchaseOrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends R2dbcRepository<CartEntity, Long> {
    Flux<CartEntity> findByIdClient(Long idCliente);
    Mono<Void> deleteByIdClient(Long idClient);
}
